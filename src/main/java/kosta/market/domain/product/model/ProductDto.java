package kosta.market.domain.product.model;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString

// 상품상세보기 및 상품수정에 필요한 정보
public class ProductDto {

    private int product_id; //상품식별번호
    private String product_name; // 상품명
    private int product_price; // 상품가격
    private String product_img_file_name; //상품이미지파일명
    private String product_img_path; // 상품이미지저장경로
    private String product_description; // 상품상세설명
    private int product_quantity; //재고수량
    private MultipartFile product_img_file;
}
