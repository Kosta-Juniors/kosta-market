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
	private Integer userId;
	private String username;
	private String password;
	private String name;
	private String contact;
	private Integer sellerId;

	public User(Integer userId, String username, String password, String name, String contact) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.name = name;
		this.contact = contact;
	}
}