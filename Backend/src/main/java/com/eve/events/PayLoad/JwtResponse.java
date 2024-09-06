package com.eve.events.PayLoad;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {

	private String token;
	private UserDto user;
	
}
