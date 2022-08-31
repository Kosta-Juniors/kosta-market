package kosta.market.domain.product.model;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class CommentListDto {

    private Integer commentId; // 댓글 식별 번호
    private Integer orderId; // 댓글 삭제를 위한 변
    private Integer productId;// 댓글 수정을 위한 변수

    private String name; // 댓글 단 사람 아이디
    private Integer score; // 평점
    private String content; // 댓글
    private Date createdAt; // 댓글 작성 시간

}
