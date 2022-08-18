package kosta.market.domain.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

	private Integer user_id;
	private String username;
	private String password;
	private String name;
	private String contact;
	private Integer seller_id;

	public User(Integer user_id, String username, String password, String name, String contact) {
		this.user_id = user_id;
		this.username = username;
		this.password = password;
		this.name = name;
		this.contact = contact;
	}
}