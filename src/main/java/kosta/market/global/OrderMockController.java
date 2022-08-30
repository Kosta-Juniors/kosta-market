package kosta.market.global;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderMockController {
	@PostMapping("/api/order")
	public ResponseEntity addOrder(@RequestParam String productList){
		System.out.println(productList);
		List<Map<String, Object>> pList = new ArrayList<>();

		for (String el : productList.split(",")) {
			String[] dict = el.split(":");
			Map<String, Object> data = new HashMap<>();

			data.put("product", dict[0]);
			data.put("quantity", dict[1]);

			pList.add(data);
		}

		for(Map<String, Object> item : pList){
			System.out.println(item);
		}


		return ResponseEntity.ok("");
	}
}
