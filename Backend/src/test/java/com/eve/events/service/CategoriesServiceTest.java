package com.eve.events.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import com.eve.events.Exceptions.ResourceNotFoundException;
import com.eve.events.PayLoad.CategoriesDto;
import com.eve.events.entity.Categories;
import com.eve.events.repository.CategoriesRepository;

@SpringBootTest
public class CategoriesServiceTest {

	@InjectMocks
	private CategoriesService categoriesService;

	@Mock
	private CategoriesRepository catRepo;

	@Mock
	private ModelMapper mapper;

	@Test
	public void testCreate_ForValidCategory() {
		CategoriesDto catDto = new CategoriesDto(1, "concert");
		Categories cat = new Categories(1, "concert", null);

		when(mapper.map(catDto, Categories.class)).thenReturn(cat);
		when(catRepo.save(cat)).thenReturn(cat);
		when(mapper.map(cat, CategoriesDto.class)).thenReturn(catDto);

		CategoriesDto result = categoriesService.create(catDto);

		verify(catRepo, times(1)).save(cat);
		assertSame(catDto, result);
	}

	@Test
	public void testCreate_ForInValidCategory() {
		CategoriesDto catDto = new CategoriesDto(1, "concert");
		Categories cat = new Categories(1, "concert", null);

		when(mapper.map(catDto, Categories.class)).thenReturn(cat);
		when(mapper.map(cat, CategoriesDto.class)).thenReturn(catDto);

		when(catRepo.findById(anyInt())).thenReturn(Optional.of(cat));

		String expectedMessage = "Category already exists";
		when(catRepo.findById(1)).thenReturn(Optional.of(cat));
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			categoriesService.create(catDto);
		});
		String actualMessage = exception.getMessage();
		verify(catRepo, never()).save(cat);
		assertEquals(expectedMessage, actualMessage);
	}

	@Test
	public void testUpdate_ForCategoryFound() {
		int catid = 1;
		CategoriesDto newcat = new CategoriesDto(1, "dance workshops");
		Categories oldCat = new Categories(1, "dance", null);

		when(catRepo.findById(catid)).thenReturn(Optional.of(oldCat));
		when(catRepo.save(oldCat)).thenReturn(oldCat);
		when(mapper.map(oldCat, CategoriesDto.class)).thenReturn(newcat);

		CategoriesDto result = categoriesService.update(newcat, catid);

		verify(catRepo, times(1)).save(oldCat);
		assertSame(newcat, result);
	}

	@Test
	public void testUpdate_ForCategoryNotFound() {
		int catId = 1;
		CategoriesDto newCat = new CategoriesDto(1, "concert");
		when(catRepo.findById(anyInt())).thenReturn(Optional.empty());
		String expectedMessage = "Category Id not Found";
		// Act & Assert
		Exception exception = assertThrows(ResourceNotFoundException.class,
				() -> categoriesService.update(newCat, catId));
		String actualMessage = exception.getMessage();
		verify(catRepo, times(1)).findById(catId);
		verify(catRepo, never()).save(any(Categories.class));
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	public void testDelete_ForCategoryIdFound() {
		int catid = 1;
		Categories cat = new Categories(1, "concert", null);

		when(catRepo.findById(catid)).thenReturn(Optional.of(cat));

		categoriesService.delete(catid);

		verify(catRepo, times(1)).delete(cat);
	}

	@Test
	public void testDelete_ForCategoryIdNotFound() {
		int catId = 1;
		when(catRepo.findById(catId)).thenReturn(Optional.empty());

		String expectedMessage = "Category Id not Found";
		// Act & Assert
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> categoriesService.delete(catId));
		verify(catRepo, times(1)).findById(catId);
		verify(catRepo, never()).delete(any(Categories.class));

		String actualMessage = exception.getMessage();
		assertEquals(expectedMessage, actualMessage);
	}

	@Test
	public void testGetById_ForCategoryIdFound() {
		int catid = 1;
		CategoriesDto catDto = new CategoriesDto(1, "concert");
		Categories cat = new Categories(1, "concert", null);

		when(catRepo.findById(catid)).thenReturn(Optional.of(cat));
		when(mapper.map(cat, CategoriesDto.class)).thenReturn(catDto);

		CategoriesDto result = categoriesService.getbyId(catid);

		assertSame(catDto, result);
	}

	@Test
	public void testGetById_ForCategoryIdNotFound() {
		int catId = 1;
		when(catRepo.findById(anyInt())).thenReturn(Optional.empty());

		String expectedMessage = "Category Id not Found";
		// Act & Assert
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> categoriesService.getbyId(catId));
		verify(catRepo, times(1)).findById(catId);
		String actualMessage = exception.getMessage();
		assertEquals(expectedMessage, actualMessage);
	}

	@Test
	public void testGetAll() {
		List<Categories> cats = new ArrayList<>();
		Categories cat1 = new Categories(1, "concert", null);
		Categories cat2 = new Categories(2, "workshops", null);
		cats.add(cat1);
		cats.add(cat2);
		List<CategoriesDto> catDtos = new ArrayList<>();
		CategoriesDto catDto1 = new CategoriesDto(1, "concert");
		CategoriesDto catDto2 = new CategoriesDto(1, "workshops");
		catDtos.add(catDto1);
		catDtos.add(catDto2);
		when(catRepo.findAll()).thenReturn(cats);
		when(mapper.map(cat1, CategoriesDto.class)).thenReturn(catDto1);
		when(mapper.map(cat2, CategoriesDto.class)).thenReturn(catDto2);

		List<CategoriesDto> result = categoriesService.getAll();

		// assertSame(catDtos, result);
		assertEquals(catDtos.size(), result.size());
		for (int i = 0; i < result.size(); i++) {
			assertEquals(catDtos.get(i), result.get(i));
		}

	}

	@Test
	public void testGetAll_ForEmptyList() {
		List<Categories> cats = new ArrayList<>();
		when(catRepo.findAll()).thenReturn(cats);
		String expectedMessage = "Categories list is empty";
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			categoriesService.getAll();
		});
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}
}
