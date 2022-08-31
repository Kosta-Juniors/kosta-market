package kosta.market.domain.product.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString


public class CommentDto {

    private Integer commentId; // 댓글 식별 번호
    private Integer orderId; // 주문식별번호
    private Integer productId; // 상품식별번호
    private Integer score; // 별점 1~10점
    private String content; // 댓글 내용

    
    // 이 외 DB에 저장된 변수
    // deleted 댓글 삭제 유무
    // createdAt 댓글 작성 시간
    // modifiedAt 댓글 수정 시간
    // deletedAt 댓글 삭제 시간
}
