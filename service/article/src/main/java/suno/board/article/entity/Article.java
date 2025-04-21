package suno.board.article.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Table(name = "article")
@Getter
@Setter
@ToString
@Entity
public class Article {
    @Id
    private Long articleId;
    private String title;
    private String content;
    @Column(name = "board_id")
    private Long boardId; // shard key
    private Long writerId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static Article create(Long articleId, String title, String content, Long boardId, Long writerId) {
        Article article = new Article();
        article.setArticleId(articleId);
        article.setTitle(title);
        article.setContent(content);
        article.setBoardId(boardId);
        article.setWriterId(writerId);
        article.setCreatedAt(LocalDateTime.now());
        article.setModifiedAt(LocalDateTime.now());
        return article;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
        modifiedAt = LocalDateTime.now();
    }

}
