package com.eve.events.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


import com.eve.events.PayLoad.UserDto;
import com.eve.events.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserService userService;

//	@PostMapping("/create")
//	public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
//		UserDto createdUser = this.userService.createUser(userDto);
//		return new ResponseEntity<UserDto>(createdUser, HttpStatus.CREATED);
//	}
//	
//	@PostMapping("/login")
//	public ResponseEntity<JwtResponse> LoginUser(@RequestBody JwtRequest jwtRequest) {
//		JwtResponse user = this.userService.login(jwtRequest);
//		return new ResponseEntity<JwtResponse>(user, HttpStatus.CREATED);
//	}

	@GetMapping("/admin/users/viewall")
	public ResponseEntity<List<UserDto>> viewAllUsers() {
		log.info("Fetching all users");
		List<UserDto> listUsers = this.userService.getAllUsers();
		log.info("Fetched {} users", listUsers.size());
		return new ResponseEntity<List<UserDto>>(listUsers, HttpStatus.ACCEPTED);
	}

	@PutMapping("/user/users/update/{userId}")
	public ResponseEntity<UserDto> updateUser(@PathVariable int userId, @RequestBody UserDto userDto) {
		log.info("Updating user with id: {}", userId);
		UserDto updatedUser = this.userService.updateUser(userId, userDto);
		log.info("User updated successfully with id: {}", userId);
		return new ResponseEntity<UserDto>(updatedUser, HttpStatus.CREATED);
	}

	@DeleteMapping("/admin/users/delete/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable int userId) {
		log.info("Deleting user with id: {}", userId);
		userService.deleteUser(userId);
		log.info("User deleted successfully with id: {}", userId);
		return new ResponseEntity<String>("User Deleted", HttpStatus.OK);
	}
	
	@GetMapping("/admin/users/getbyid/{userId}")
	public ResponseEntity<UserDto> getByUserId(@PathVariable int userId) {
		log.info("Fetching user with id: {}", userId);
		UserDto user = this.userService.getUserByUserId(userId);
		log.info("User fetched successfully with id: {}", userId);
		return new ResponseEntity<UserDto>(user, HttpStatus.OK);
	}

}
