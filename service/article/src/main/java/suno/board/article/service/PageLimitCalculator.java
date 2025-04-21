package suno.board.article.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

// util성 클래스라 프라이빗 생성자
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PageLimitCalculator {

    public static Long calculatePageLimit(Long page, Long pageSize, Long movablePageCount) {
        return (((page - 1) / movablePageCount) + 1) * pageSize * movablePageCount + 1;
    }
}
