package com.eve.events.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eve.events.Exceptions.ResourceNotFoundException;
import com.eve.events.PayLoad.CategoriesDto;
import com.eve.events.entity.Categories;
//import com.eve.events.repository.BookingRepository;
import com.eve.events.repository.CategoriesRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CategoriesService {

	@Autowired
	private CategoriesRepository catRepo;
	

	@Autowired
	private ModelMapper mapper;

	public CategoriesDto create(CategoriesDto dto) {

		log.info("Creating category with DTO: {}", dto);

		Categories cat = toEntity(dto);
		
		Optional<Categories> check1 = catRepo.findById(cat.getCategoryId());
		Optional<Categories> check2 = catRepo.findByCategoryName(cat.getCategoryName());

		if (check1.isPresent() || check2.isPresent()) {
			log.error("Category already exists");
			throw new ResourceNotFoundException("Category already exists");
		}
		
		Categories saved = catRepo.save(cat);
		log.info("Created category with id {}", saved.getCategoryId());
		return toDto(saved);
	}

	public CategoriesDto update(CategoriesDto newcat, int catid) {
		log.info("Updating category with ID: {} and new DTO: {}", catid, newcat);
		Categories oldCat = this.catRepo.findById(catid)
				.orElseThrow(() -> new ResourceNotFoundException("Category Id not Found"));
		oldCat.setCategoryName(newcat.getCategoryName());
		Categories save = this.catRepo.save(oldCat);
		log.info("Updated category with id {}", save.getCategoryId());
		return toDto(save);
	}

	public void delete(int catid) {
		log.info("Deleting category with ID: {}", catid);

		Categories Cat = this.catRepo.findById(catid)
				.orElseThrow(() -> new ResourceNotFoundException("Category Id not Found"));
		
		this.catRepo.delete(Cat);
		log.info("Deleted category with id {}", catid);
	}

	public CategoriesDto getbyId(int catid) {
		 log.info("Fetching category with ID: {}", catid);
		Categories getByid = this.catRepo.findById(catid)
				.orElseThrow(() -> new ResourceNotFoundException("Category Id not Found"));
		log.info("Fetched category with id {}", catid);
		return toDto(getByid);
	}

	public List<CategoriesDto> getAll() {
		  log.info("Fetching all categories");
		List<Categories> findAll = this.catRepo.findAll();
		if(findAll.size()==0)
			throw new ResourceNotFoundException("Categories list is empty");
		List<CategoriesDto> allDto = findAll.stream().map(cat -> toDto(cat)).collect(Collectors.toList());
		log.info("Fetched all categories");
		return allDto;
	}

	public CategoriesDto toDto(Categories cat) {
		return this.mapper.map(cat, CategoriesDto.class);
	}

	public Categories toEntity(CategoriesDto catDto) {
		return this.mapper.map(catDto, Categories.class);
	}
}
