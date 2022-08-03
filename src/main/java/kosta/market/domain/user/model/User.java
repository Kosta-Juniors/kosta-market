package kosta.market.domain.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class User {

	private int user_id;
	private String username;
	private String password;
	private String name;
	private String contact;
	private Integer seller_id;

	public User(int user_id, String username, String password, String name, String contact) {
		this.user_id = user_id;
		this.username = username;
		this.password = password;
		this.name = name;
		this.contact = contact;
	}
}