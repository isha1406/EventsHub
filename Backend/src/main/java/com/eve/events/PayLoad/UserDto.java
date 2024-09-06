package com.eve.events.PayLoad;


import com.eve.events.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

	private int userId;
	private String email;
	private String firstName;
	private String lastName;
	private String mobileNumber;
	private String password;
	private Role role;
}
