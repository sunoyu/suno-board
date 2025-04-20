package suno.board.article.service.request;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ArticleCreateRequest {
    private String title;
    private String content;
    private Long articleId;
    private Long boardId;
    private Long writerId;

}
