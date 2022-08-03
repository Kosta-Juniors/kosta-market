package kosta.market.domain.product.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ProductListDto {

    private int product_id; // 상품식별번호
    private String product_name; //상품명
    private int product_price; //상품가격
    private int product_quantity; // 구매 가능여부용

    //user용
    private String product_img_file_name; // 이미지 파일 이름
    private String product_img_path; // 이미지 파일 경로
     // 추후 검색 기능 및 표시 관련해서 의견을 들어보고 결정
//    private int sellerId or userName;
//    private int categoryId;


}
