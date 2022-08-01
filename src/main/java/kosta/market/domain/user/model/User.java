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
	private int userId;
	private String username;
	private String password;
	private String name;
	private String contact;
}