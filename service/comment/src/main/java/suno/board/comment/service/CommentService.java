package suno.board.comment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import suno.board.comment.entity.Comment;
import suno.board.comment.repository.CommentRepository;
import suno.board.comment.service.request.CommentCreateRequest;
import suno.board.comment.service.response.CommentResponse;
import suno.board.common.snowflake.Snowflake;

import static java.util.function.Predicate.not;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final Snowflake snowflake = new Snowflake();

    @Transactional
    public CommentResponse create(CommentCreateRequest request) {
        Comment parent = findParent(request);
        Comment comment = commentRepository.save(
                Comment.create(
                        snowflake.nextId(),
                        request.getContent(),
                        parent == null ? null : parent.getCommentId(),
                        request.getArticleId(),
                        request.getWriterId()
                )
        );
        return CommentResponse.from(comment);
    }

    private Comment findParent(CommentCreateRequest request) {
        Long parentCommentId = request.getParentCommentId();
        if (parentCommentId == null) {
            return null;
        }
        return commentRepository.findById(parentCommentId)
                .filter(not(Comment::getDeleted))
                .filter(Comment::isRoot)
                .orElseThrow();
    }

    public CommentResponse read(Long commentId) {
        return CommentResponse.from(
                commentRepository.findById(commentId).orElseThrow()
        );
    }


    @Transactional
    public void delete(Long commentId) {
        commentRepository.findById(commentId)
                .filter(not(Comment::getDeleted))
                .ifPresent(comment -> {
                    if (hasChildren(comment)) {
                        comment.delete();
                        log.info("delete");
                    } else {
                        delete(comment);
                    }
                });
    }

    private void delete(Comment comment) {
        commentRepository.delete(comment);
        if (!comment.isRoot()) { // 루트 댓글이 아니라면
            commentRepository.findById(comment.getParentCommentId()) // 부모 댓글을 찾아
                    .filter(Comment::getDeleted)  // 부모 댓글도 삭제된 상태라면
                    .filter(not(this::hasChildren))    // 그 부모 댓글의 자식들도 모두 없는지 보고
                    .ifPresent(this::delete);     // 자식이 상위 댓글도 아예 삭제한다.
        }
    }

    private boolean hasChildren(Comment comment) {
        return commentRepository.countBy(comment.getArticleId(), comment.getCommentId(), 2L) == 2;     // 본인포함 depth 가 2라면 자식을 소유
    }


}
