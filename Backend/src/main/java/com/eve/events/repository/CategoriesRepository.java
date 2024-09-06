package com.eve.events.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eve.events.entity.Categories;

public interface CategoriesRepository extends JpaRepository<Categories, Integer>{

	Optional<Categories> findByCategoryName(String categoryName);

}
