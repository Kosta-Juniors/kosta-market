package kosta.market.domain.product.model;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ProductCreateDto {

    private String product_name;
    private int product_price;
    private String product_img_file_name;
    private String product_img_path;
    private String product_description;
    private int product_quantity;
    private int category_id;
    private MultipartFile product_img_file;
    //추후에 카타고리 분리필요

}
