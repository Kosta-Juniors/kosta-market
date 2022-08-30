package kosta.market.global;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductMockController {

	@PostMapping("/api/product/cart")
	public ResponseEntity 카트등록() throws JsonProcessingException, URISyntaxException {
		ObjectMapper jsonData = new ObjectMapper();

		return ResponseEntity.created(new URI("")).build();
	}

	@GetMapping("/api/category")
	public ResponseEntity 카테고리_가져오기() throws JsonProcessingException {
		ObjectMapper jsonData = new ObjectMapper();

		Map<String, Object> data = new HashMap<>();
		List<Object> categoryList = new ArrayList();

		for(int i=1; i<6; i++) {
			Map<String, Object> category = new HashMap<>();
			category.put("categoryId", i);
			category.put("categoryName", "카테고리 "+i);
			categoryList.add(category);
		}

		data.put("data", categoryList);

		return ResponseEntity.ok(jsonData.writeValueAsString(data));
	}

	@GetMapping("/api/product/{productId}")
	public ResponseEntity 상품_디테일_가져오기(@PathVariable String productId) throws JsonProcessingException {
		System.out.println("what?");
		ObjectMapper jsonData = new ObjectMapper();

		Map<String, Object> data = new HashMap<>();
		Map<String, Object> product = new HashMap<>();

		product.put("productId", productId);
		product.put("productName", "테스트 상품명");
		product.put("productDescription", "상품 설명상품 설명상품 설명상품 설명상품 설명상품 설명");
		product.put("productPrice", 15000);

		data.put("data", product);

		return ResponseEntity.ok(jsonData.writeValueAsString(data));
	}

	@GetMapping("/api/product")//?categoryId=4&page=2&size=10"
	public ResponseEntity 상품_목록_가져오기(@RequestParam(name = "categoryId", defaultValue = "1") int categoryId,
										@RequestParam(name="page", defaultValue="1") String page,
										@RequestParam(name= "size", defaultValue="12") int size,
										@RequestParam(name= "search", defaultValue = "") String searchQuery)
		throws JsonProcessingException {

		ObjectMapper jsonData = new ObjectMapper();
		Map<String, Object> data = new HashMap<>();
		List<Object> productList = new ArrayList();

		System.out.println(searchQuery);

		for(int i=0; i < size; i++){
			Map<String, Object> product = new HashMap<>();
			product.put("productId", i + 1 + size*(categoryId-1) );
			product.put("productName", "["+categoryId+"번 카테고리]"+"["+page+"페이지]"+searchQuery+i);
			product.put("productPrice", 10000 * i);
			product.put("productDescription", "설명 설명 설명 설명 설명 설명 설명 설명 설명 설명 설명 설명 설명 설명 설명");
			product.put("score", 5);
			productList.add(product);
		}

		data.put("data", productList);
		data.put("links", "");
		//"prev":"/product?categoryId=4&page=1&size=10",
		//"next":"/product?categoryId=4&page=3&size=10"

		return ResponseEntity.ok(jsonData.writeValueAsString(data));
	}
}

