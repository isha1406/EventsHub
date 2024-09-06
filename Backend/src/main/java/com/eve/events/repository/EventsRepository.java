package com.eve.events.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eve.events.entity.Categories;
import com.eve.events.entity.Events;



public interface EventsRepository extends JpaRepository<Events, Integer>{

	
	List<Events> findByCategories(Categories category);

	Optional<Events> findByEventName(String eventName);


}
