package iriro.article.scheduler;

import iriro.article.crawler.ArticleCrawler;
import iriro.article.util.RegionConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleCrawlingScheduler {

    private final ArticleCrawler crawler;

    // 매일 오전 6시 자동 실행
    @Scheduled(cron = "0 0 6 * * *")
    public void crawlAll() {
        System.out.println("=== 서울 범죄 뉴스 크롤링 테스트 시작 ===");

        // 2. 지역구 하나씩 검색
        for (String district : RegionConstants.seoulDistricts) {

            // + 선택) 검색 도움 키워드
            String keyword = district + " 범죄";

            // 1. 노컷뉴스 크롤링 (Selenium 사용)
            System.out.println("[노컷뉴스] 크롤링 시작");
            crawler.crawlNoCutNews(keyword, district);

            // 2. 머니투데이 크롤링 (Jsoup 사용)
            System.out.println("[머니투데이] 크롤링 시작");
            crawler.crawlMtNews(keyword, district);

            // 3. 안전장치: 한 지역구 끝날 때마다 대기
            try {
                System.out.println("지역 변경 중 (5초 대기)");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println("지역 변경 대기 중 오류 발생");
            }
        }
        System.out.println("=== 서울 전체 범죄 뉴스 크롤링 종료 ===");
    }
}
