package iriro.article.service;

import iriro.article.entity.ArticleEntity;
import iriro.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    // 저장
    public void saveToDb(String title, String url, String content, String siteName, String district, String keyword, String date, String writer, String pic) {
        // DB 글자 수 제한 지키기
        String safeTitle = title.length() > 95 ? title.substring(0, 95) + "..." : title;
        String safeSite = siteName.length() > 10 ? siteName.substring(0, 10) : siteName;
        String safeDate = date.length() > 10 ? date.substring(0, 10) : date;
        String safeWriter = writer.length() > 20 ? writer.substring(0, 20) : writer;
        String safePic = pic.length() > 250 ? pic.substring(0, 250) : pic;

        articleRepository.save(ArticleEntity.builder()
                .articleTitle(safeTitle)
                .articleUrl(url)
                .articleContent(content)
                .articleSite(safeSite)
                .articleDistrict(district)
                .articleKeyword(keyword) // 추가 필요
                .articleDate(safeDate)
                .articleWriter(safeWriter)
                .articlePic(safePic)
                .build());

        System.out.println("저장 완료 [" + safeSite + "]: " + safeTitle);
    }

}
