package com.eve.events.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eve.events.Exceptions.ResourceNotFoundException;
import com.eve.events.PayLoad.JwtRequest;
import com.eve.events.PayLoad.JwtResponse;
import com.eve.events.PayLoad.UserDto;
import com.eve.events.entity.Role;
import com.eve.events.entity.Users;
import com.eve.events.repository.UsersRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {
	

	private final UsersRepository usersRepository;


	private final ModelMapper mm;


	private final PasswordEncoder passwordEncoder;
	
	private final JwtService jwtService;

	private final AuthenticationManager authenticationManager;
	

	public JwtResponse register(Users user) {

		 log.info("Registering user {}", user.getEmail());
		//Users user = toEntity(userDto);

		Optional<Users> check = usersRepository.findByEmail(user.getEmail());
		if (check.isPresent()) {
			throw new ResourceNotFoundException("User already exits");
		}

//		user.setRole(Role.ADMIN);
		user.setRole(Role.USER);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		Users savedUser = usersRepository.save(user);
		UserDto savUserDto = toDto(savedUser);
		String token =jwtService.generateToken(savedUser);
		JwtResponse jwtResponse=new JwtResponse();
		jwtResponse.setToken(token);
		jwtResponse.setUser(savUserDto);

        log.info("User registered successfully");

		return jwtResponse;
	}
	
	
	public JwtResponse login(JwtRequest request) {
		log.info("Authenticating user {}", request.getUsername());
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		Users user = usersRepository.findByEmail(request.getUsername()).orElseThrow(() -> new ResourceNotFoundException("User does not exists"));
		String token = jwtService.generateToken(user);

		UserDto userDto=toDto(user);
		JwtResponse jwtResponse=new JwtResponse();
		jwtResponse.setToken(token);
		jwtResponse.setUser(userDto);
		log.info("User authenticated successfully");
		return jwtResponse;
	}

	public UserDto toDto(Users u) {
		return this.mm.map(u, UserDto.class);
	}

	public Users toEntity(UserDto dto) {
		return this.mm.map(dto, Users.class);
	}

}

