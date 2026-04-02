package iriro.article.controller;

import iriro.article.scheduler.ArticleCrawlingScheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleCrawlingScheduler crawler;

    @GetMapping("/crawl")
    public String startCrawlingTest() {
        System.out.println("크롤링 수동 시작");
        crawler.crawlAll();
        return "크롤링 테스트 완료";
    }
}