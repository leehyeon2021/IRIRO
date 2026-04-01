package iriro.article.service;

import io.github.bonigarcia.wdm.WebDriverManager;
import iriro.article.entity.ArticleEntity;
import iriro.article.repository.ArticleRepository;
import iriro.article.util.CrimeNewsFilter;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleCrawlingService {

    private final ArticleRepository articleRepository;
    private final CrimeNewsFilter filter;

    // 매일 오전 6시 자동 실행
    //@Scheduled(cron = "0 0 6 * * *")
    public void crawlAll() {
        System.out.println("=== 서울 범죄 뉴스 크롤링 테스트 시작 ===");

        // 검색할 키워드 (테스트용이므로 고정값 사용)
        String keyword = "서울 흉기";

        // 1. 노컷뉴스 크롤링 (Selenium 사용)
        System.out.println("[노컷뉴스] 수집을 시작합니다.");
        crawlNoCutNews(keyword);

        // 2. 머니투데이 크롤링 (Jsoup 사용)
        System.out.println("[머니투데이] 수집을 시작합니다.");
        crawlMtNews(keyword);

        System.out.println("=== 범죄 뉴스 크롤링 테스트 완료 ===");
    }

    // 1. 노컷뉴스 크롤러 (Selenium으로 목록 가져오기 -> Jsoup으로 본문 읽기)
    private void crawlNoCutNews(String keyword) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new", "--disable-gpu");
        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            String searchUrl = "https://search.nocutnews.co.kr/list?query=" + keyword;
            driver.get(searchUrl);

            // 검색 결과가 뜰 때까지 대기
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".content > #news > .newslist")));

            // 기사 목록 싹 다 가져오기 (1페이지)
            List<WebElement> articles = driver.findElements(By.cssSelector(".newslist > li"));

            // 안전장치 1: 몇 개 가져왔는지 세는 카운터
            int count = 0;

            for (WebElement article : articles) {
                // 안전장치 1: 5개 다 채웠으면 반복문을 강제 종료
                if (count >= 5) {
                    System.out.println("안전을 위해 5개만 수집하고 노컷뉴스를 빠져나갑니다.");
                    break;
                }

                String title = article.findElement(By.cssSelector("a > strong")).getText().trim();
                String url = article.findElement(By.cssSelector("a")).getAttribute("href");

                if (url.isEmpty() || articleRepository.existsByArticleUrl(url)) continue;

                // 본문 긁어오기
                String content = fetchContent(url);

                if (!filter.isValid(title, content)) continue;

                saveToDb(title, url, content, "노컷뉴스", keyword);

                // 저장 성공 카운터 1 증가
                count++;

                // 안전장치 2: 1.5초
                System.out.println("2.7초 대기");
                Thread.sleep(2700);
            }
        } catch (Exception e) {
            System.out.println("노컷뉴스 오류: " + e.getMessage());
        } finally {
            driver.quit();
        }
    }

    // 2. 머니투데이 크롤러 (목록, 본문 전부 Jsoup)
    private void crawlMtNews(String keyword) {
        try {
            String searchUrl = "https://www.mt.co.kr/search?startDate=20260101&endDate=20260401&filter=contents&order=accuracy&keyword=" + keyword;

            Document doc = Jsoup.connect(searchUrl)
                    .userAgent("Mozilla/5.0")
                    .timeout(5000)
                    .get();

            Elements articles = doc.select(".list_news > li");

            int count = 0; // 안전장치 1: 개수 세는 카운터

            for (Element article : articles) {
                // 안전장치 2: 5개 다 채웠으면 반복문을 강제 종료
                if (count >= 5) {
                    System.out.println("안전을 위해 5개만 수집하고 머니투데이를 빠져나갑니다.");
                    break;
                }

                try {
                    String title = article.select("a > .headline news--tertiary").text().trim();
                    String url = article.select(".article_item > a").attr("abs:href");

                    // URL 없거나 이미 저장된 기사 건너뜀
                    if (title.isEmpty() || url.isEmpty() || articleRepository.existsByArticleUrl(url)) {
                        continue;
                    }

                    // 상세 페이지 본문 전체 가져오기
                    String content = fetchContent(url);

                    // 필터 통과 못 하면 건너뜀
                    if (!filter.isValid(title, content)) continue;

                    // 저장
                    saveToDb(title, url, content, "머니투데이", keyword);

                    count++; // 성공 카운터 1 증가

                    // 안전장치 3: 2.7초
                    System.out.println("2.7초 대기");
                    Thread.sleep(2700);

                } catch (Exception innerE) {
                    // 기사 하나가 에러 나도 전체가 멈추지 않게
                    System.out.println("머니투데이 개별 기사 건너뜀: " + innerE.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("머니투데이 오류: " + e.getMessage());
        }
    }

    // 3. 기사 본문 상세
    private String fetchContent(String articleUrl) {
        try {
            Document doc = Jsoup.connect(articleUrl)
                    .userAgent("Mozilla/5.0")
                    .timeout(5000)
                    .get();

            // 머니투데이(#textBody), 노컷뉴스(#pnlContent) 및 범용 뉴스 사이트 본문 태그를 모두 포괄하는 선택자
            Element body = doc.selectFirst("div#textBody, div#pnlContent, article, div.article-body, div.news-body, div#articleBody");

            return body != null ? body.text() : "";
        } catch (Exception e) {
            return "";
        }
    }

    // 4. 저장
    private void saveToDb(String title, String url, String content, String siteName, String keyword) {
        // DB 글자 수 제한 방어 로직 (articleTitle: 100자, articleSite: 10자)
        String safeTitle = title.length() > 95 ? title.substring(0, 95) + "..." : title;
        String safeSite = siteName.length() > 10 ? siteName.substring(0, 10) : siteName;

        articleRepository.save(ArticleEntity.builder()
                .articleTitle(safeTitle)
                .articleUrl(url)
                .articleContent(content)
                .articleSite(safeSite)
                .articleKeyword(keyword) // "서울 범죄"
                .articleDate("")   // 현재 버전 미사용 (빈값)
                .articlePic("")    // 현재 버전 미사용 (빈값)
                .articleWriter("") // 현재 버전 미사용 (빈값)
                .build());

        System.out.println("저장 완료 [" + safeSite + "]: " + safeTitle);
    }

}
