package com.eve.events.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.eve.events.PayLoad.CategoriesDto;
import com.eve.events.service.CategoriesService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/api")
//@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoriesController {

	@Autowired
	private CategoriesService catService;

	// Create Category
	@PostMapping("/admin/categories/create")
	public ResponseEntity<CategoriesDto> create(@RequestBody CategoriesDto catDto) {
		log.info("Creating a new category: {}", catDto);
		CategoriesDto create = this.catService.create(catDto);
		log.info("Category created successfully with id: {}", create.getCategoryId());
		return new ResponseEntity<CategoriesDto>(create, HttpStatus.CREATED);
	}

	// UpdateCategory
	@PutMapping("/admin/categories/update/{catid}")
	public ResponseEntity<CategoriesDto> update(@RequestBody CategoriesDto catDto, @PathVariable int catid) {
		log.info("Updating category with ID {}: {}", catid, catDto);
		CategoriesDto update = this.catService.update(catDto, catid);
		log.info("Category updated successfully with id: {}", catid);
		return new ResponseEntity<CategoriesDto>(update, HttpStatus.OK);
	}

	// delete Category
	@DeleteMapping("/admin/categories/delete/{catId}")
	public ResponseEntity<String> delete(@PathVariable int catId) {
		log.info("Deleting category with ID: {}", catId);
		catService.delete(catId);
		log.info("Category deleted successfully with id: {}", catId);
		return new ResponseEntity<String>("Category Deleted", HttpStatus.OK);
	}

	// getCategoryById
	@GetMapping("/categories/getById/{catId}")
	public ResponseEntity<CategoriesDto> getByid(@PathVariable int catId) {
		log.info("Fetching Category with id: {}", catId);
		CategoriesDto getbyId = this.catService.getbyId(catId);
		log.info("Category fetched successfully with id: {}", catId);
		return new ResponseEntity<CategoriesDto>(getbyId, HttpStatus.OK);
	}

	// Get All Category
	@GetMapping("/categories/getAll")
	public ResponseEntity<List<CategoriesDto>> getAll() {
		log.info("Fetching all events");
		List<CategoriesDto> allCategoriesDtos = this.catService.getAll();
		log.info("Fetched {} categories", allCategoriesDtos.size());
		return new ResponseEntity<List<CategoriesDto>>(allCategoriesDtos, HttpStatus.OK);
	}

}
