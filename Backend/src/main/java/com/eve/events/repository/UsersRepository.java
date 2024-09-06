package com.eve.events.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eve.events.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Integer> {
	
	public Optional<Users>findByEmail(String email);


}
