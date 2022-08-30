package kosta.market.global;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.servlet.http.HttpSession;
import kosta.market.global.util.SecurityUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserMockController {

	@PostMapping("/api/user/signin")
	public ResponseEntity userLogin(@RequestBody Map<String, Object> body){
		String username = body.get("username").toString();
		String password = body.get("password").toString();

		System.out.println("Receive login : " + body.toString());
		if(Objects.deepEquals(username, "admin") && Objects.deepEquals(password, "1111")) {
			SecurityUtil.setAuthentication("1");
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping("/api/user/signup")
	public ResponseEntity userRegister(@RequestBody Map<String, Object> body){

		System.out.println("Receive Register : " + body.toString());
		String username = body.get("username").toString();
		String password = body.get("password").toString();

		return ResponseEntity.ok().build();
	}

	@GetMapping("/api/user")
	public ResponseEntity getUser() throws JsonProcessingException {
		if(!SecurityUtil.isAuthenticated()){
			return ResponseEntity.notFound().build();
		}

		ObjectMapper jsonData = new ObjectMapper();
		Map<String, Object> data = new HashMap<>();
		Map<String, Object> user = new HashMap<>();

		user.put("name", "완규");
		user.put("phone", "010-9099-9999");

		data.put("data", user);

		return ResponseEntity.ok(jsonData.writeValueAsString(data));
	}

	public ResponseEntity modUser() throws JsonProcessingException {
		if(!SecurityUtil.isAuthenticated()){
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/api/user/signout")
	public ResponseEntity signout(){

		if(SecurityUtil.isAuthenticated()){
			SecurityUtil.getCurrentSession().invalidate();
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
	@DeleteMapping("tesesa")
	public ResponseEntity retireUser(){

		if(SecurityUtil.isAuthenticated()){
			SecurityUtil.getCurrentSession().invalidate();
			return ResponseEntity.ok(null);
		}
		return ResponseEntity.notFound().build();
	}
//	@PutMapping
//	@PostMapping
//	@DeleteMapping
//	@PostMapping
//	@DeleteMapping
}
