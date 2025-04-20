package suno.board.article.service.response;

import lombok.Getter;
import lombok.ToString;
import suno.board.article.entity.Article;

import java.time.LocalDateTime;

@Getter
@ToString
public class ArticleResponse {
    private Long articleId;
    private String title;
    private String content;
    private Long boardId; // shard key
    private Long writerId;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public static ArticleResponse from(Article article) {
        ArticleResponse response = new ArticleResponse();
        response.articleId = article.getArticleId();   // 캡슐화죠~ setter없이 private 필드에 접근해서
        response.title = article.getTitle();
        response.content = article.getContent();
        response.boardId = article.getBoardId();
        response.writerId = article.getWriterId();
        response.createAt = article.getCreatedAt();
        response.modifiedAt = article.getModifiedAt();
        return response;
    }
}
