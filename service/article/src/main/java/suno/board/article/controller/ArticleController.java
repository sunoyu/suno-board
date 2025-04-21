package suno.board.article.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import suno.board.article.service.ArticleService;
import suno.board.article.service.request.ArticleCreateRequest;
import suno.board.article.service.request.ArticleUpdateRequest;
import suno.board.article.service.response.ArticlePageResponse;
import suno.board.article.service.response.ArticleResponse;

@RestController
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping("/v1/articles/{articleId}")
    public ArticleResponse read(@PathVariable Long articleId) {
        return articleService.read(articleId);
    }

    @GetMapping("/v1/articles")
    public ArticlePageResponse readAll(@RequestParam("boardId") Long boardId,
                                       @RequestParam("page") Long page,
                                       @RequestParam("pageSize") Long pageSize) {
        return articleService.readAll(boardId, page, pageSize);
    }


    @PostMapping("/v1/articles")
    public ArticleResponse create(@RequestBody ArticleCreateRequest request) {
        return articleService.create(request);
    }
    @PutMapping("/v1/articles/{articleId}")
    public ArticleResponse update(Long articleId, ArticleUpdateRequest request) {
        return articleService.update(articleId, request);
    }
    @DeleteMapping("/v1/articles/{articleId}")
    public void delete(@PathVariable Long articleId) {
        articleService.delete(articleId);
    }
}
