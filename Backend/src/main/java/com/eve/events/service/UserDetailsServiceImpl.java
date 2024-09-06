package com.eve.events.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.eve.events.repository.UsersRepository;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UsersRepository userRepository;

	public UserDetailsServiceImpl(UsersRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		log.info("Loading user by username: {}", username);
		return userRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found."));
		
	}

}