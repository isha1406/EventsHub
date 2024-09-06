package com.eve.events.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eve.events.Exceptions.ResourceNotFoundException;
import com.eve.events.PayLoad.UserDto;
import com.eve.events.entity.Users;
import com.eve.events.repository.UsersRepository;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class UserService {

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	private ModelMapper mm;
	
	@Autowired
	private PasswordEncoder passwordEncoder;


//	public UserDto createUser(UserDto userDto) {
//
//		Users user = toEntity(userDto);
//
//		Optional<Users> check = usersRepository.findByEmail(user.getEmail());
//		if (check.isPresent()) {
//			throw new ResourceNotFoundException("User already exits");
//		}
//
//		// user.setRole(Role.USER);
//		user.setPassword(passwordEncoder.encode(user.getPassword()));
//
//		Users savedUser = usersRepository.save(user);
//		UserDto savUserDto = toDto(savedUser);
//		return savUserDto;
//	}
//
//	

	public List<UserDto> getAllUsers() {
		log.info("Getting all users");
		List<Users> allUser = usersRepository.findAll();
		if(allUser.size()==0)
			throw new ResourceNotFoundException("Users list is empty");
		List<UserDto> allUserDto = allUser.stream().map(user -> this.toDto(user)).collect(Collectors.toList());
		log.info("Returning all user DTOs");
		return allUserDto;
	}

	public UserDto updateUser(int userId, UserDto newUser) {
		log.info("Updating user with ID: {}", userId);
		Users oldUser = usersRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));
		oldUser.setEmail(newUser.getEmail());
		oldUser.setFirstName(newUser.getFirstName());
		oldUser.setLastName(newUser.getLastName());
		oldUser.setMobileNumber(newUser.getMobileNumber());
		oldUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
		Users saveUser = usersRepository.save(oldUser);
		UserDto upUserDto = toDto(saveUser);
		log.info("Returning updated user DTO");
		return upUserDto;
	}

	public void deleteUser(int userId) {

		log.info("Attempting to delete user with id: {}", userId);
		Users byId = usersRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(+userId + "User not Found"));

		this.usersRepository.delete(byId);
		log.info("Successfully deleted user with id: {}", userId);
	}

	public UserDto getUserByUserId(int userId) {
		log.info("Attempting to get user with id: {}", userId);
		Users user = usersRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not Found"));
		log.info("Successfully retrieved user with id: {}", userId);
		return toDto(user);
	}

	public UserDto getUserByEmail(String email) {
		log.info("Attempting to load user with email: {}", email);
		Users users = usersRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User not Found"));
		log.info("Successfully loaded user with email: {}", email);
		return toDto(users);
	}

	public UserDto toDto(Users u) {
		return this.mm.map(u, UserDto.class);
	}

	public Users toEntity(UserDto dto) {
		return this.mm.map(dto, Users.class);
	}

}
