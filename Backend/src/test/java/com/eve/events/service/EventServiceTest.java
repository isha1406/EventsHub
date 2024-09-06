package com.eve.events.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import com.eve.events.Exceptions.ResourceNotFoundException;
import com.eve.events.PayLoad.EventsDto;
import com.eve.events.PayLoad.EventsListByCat;
import com.eve.events.entity.Categories;
import com.eve.events.entity.Events;
import com.eve.events.repository.CategoriesRepository;
import com.eve.events.repository.EventsRepository;

@SpringBootTest
public class EventServiceTest {

	@InjectMocks
	EventsService eventsService;

	@Mock
	EventsRepository eventsRepo;

	@Mock
	private CategoriesRepository categoriesRepository;

	@Mock
	private ModelMapper modelMapper;

	@Test
	public void testDeleteEvent_forEventFound() {
		// Create mock data
		Events event = new Events(1, "sunidhi", "concert", 2000, 500, "chennai","xyz", LocalDate.now(), LocalTime.now(),
				null);

		// Define the behavior of the mocked dependencies
		when(eventsRepo.findById(event.getEventId())).thenReturn(Optional.of(event));

		// Call the method under test
		eventsService.deleteEvent(event.getEventId());

		// Verify the results
		verify(eventsRepo, times(1)).delete(event);
	}

	@Test
	public void testDeleteEvent_forEventNotFound() {
		// Arrange
		int eventId = 1;
		when(eventsRepo.findById(eventId)).thenReturn(Optional.empty());

		String expectedMessage = "Event not Found";
		// Act & Assert
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> eventsService.deleteEvent(eventId));
		
		verify(eventsRepo, times(1)).findById(eventId);
		verify(eventsRepo, never()).delete(any(Events.class));
		
		String actualMessage = exception.getMessage();
		assertEquals(expectedMessage, actualMessage);
	}

	@Test
	public void testCreateEvent_forValidEvent() {

		EventsDto eventsDto = new EventsDto(1, "sunidhi", "concert", 2000, 500, "chennai","xyz", LocalDate.now(),
				LocalTime.now(),null);
		Categories cat = new Categories(1, "concert", null);
		int catId = 1;
		Events events = mock(Events.class);

		when(categoriesRepository.findById(catId)).thenReturn(Optional.of(cat));
		when(eventsRepo.findById(events.getEventId())).thenReturn(Optional.empty());
		when(eventsRepo.findByEventName(events.getEventName())).thenReturn(Optional.empty());
		when(modelMapper.map(eventsDto, Events.class)).thenReturn(events);
		when(eventsRepo.save(events)).thenReturn(events);
		when(modelMapper.map(events, EventsDto.class)).thenReturn(eventsDto);

		EventsDto result = eventsService.createEvent(eventsDto, catId);

		assertEquals(eventsDto, result);
	}

	@Test
	public void testCreateEvent_forExistingEventId() {
		Events event = new Events(1, "sunidhi", "concert", 2000, 500, "chennai","xyz", LocalDate.now(), LocalTime.now(),
				null);
		EventsDto eventsDto = new EventsDto(1, "sunidhi", "concert", 2000, 500, "chennai","xyz", LocalDate.now(),
				LocalTime.now(),null);
		Categories cat = new Categories(1, "concert", null);

		when(modelMapper.map(eventsDto, Events.class)).thenReturn(event);
		when(modelMapper.map(event, EventsDto.class)).thenReturn(eventsDto);
		when(categoriesRepository.findById(anyInt())).thenReturn(Optional.of(cat));

		String expectedMessage = "Event already exists";
		when(eventsRepo.findById(1)).thenReturn(Optional.of(event));
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			eventsService.createEvent(eventsDto, cat.getCategoryId());
		});
		String actualMessage = exception.getMessage();
		verify(eventsRepo, never()).save(event);
		assertEquals(expectedMessage, actualMessage);
		// assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	public void testGetAllEvents() {
		// Create mock data
		List<Events> allEvents = new ArrayList<>();
		Categories cat = new Categories(1, "concert", allEvents);
		Events event1 = new Events(1, "sunidhi", "concert", 2000, 500, "chennai","xyz", LocalDate.now(), LocalTime.now(),
				cat);
		Events event2 = new Events(2, "kk", "concert", 2000, 500, "chennai","abc", LocalDate.now(), LocalTime.now(), cat);
		allEvents.add(event1);
		allEvents.add(event2);

		List<EventsDto> allEventsDto = new ArrayList<>();
		EventsDto eventsDto1 = new EventsDto(1, "sunidhi", "concert", 2000, 500, "chennai","xyz", LocalDate.now(),
				LocalTime.now(),null);
		EventsDto eventsDto2 = new EventsDto(2, "kk", "concert", 2000, 500, "chennai","xyz", LocalDate.now(),
				LocalTime.now(),null);
		allEventsDto.add(eventsDto1);
		allEventsDto.add(eventsDto2);

		// Define the behavior of the mocked dependencies
		when(eventsRepo.findAll()).thenReturn(allEvents);
		when(modelMapper.map(event1, EventsDto.class)).thenReturn(eventsDto1);
		when(modelMapper.map(event2, EventsDto.class)).thenReturn(eventsDto2);

		// Call the method under test
		List<EventsDto> result = eventsService.getAllEvnets();

		// Verify the results
		assertEquals(allEventsDto.size(), result.size());
		for (int i = 0; i < result.size(); i++) {
			assertEquals(allEventsDto.get(i), result.get(i));
		}
	}
	
	@Test
	public void testGetAllEvents_ForEmptyList() {
		// Create mock data
		String expectedMessage = "Events list is empty";
		List<Events> allEvents = new ArrayList<>();
		// Define the behavior of the mocked dependencies
		when(eventsRepo.findAll()).thenReturn(allEvents);
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
        	eventsService.getAllEvnets();
        });
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	public void testUpdateEvent_EventFound() {
		// Create mock data
		int eventId = 1;
		Events oldEvent = new Events(1, "sunidhi", "concert", 2000, 500, "chennai","xyz", LocalDate.now(), LocalTime.now(),
				null);
		EventsDto newEvent = new EventsDto(2, "kk", "concert", 2000, 500, "chennai","xyz", LocalDate.now(), LocalTime.now(),null);

		// Define the behavior of the mocked dependencies
		when(eventsRepo.findById(eventId)).thenReturn(Optional.of(oldEvent));
		when(modelMapper.map(newEvent, Events.class)).thenReturn(oldEvent);
		when(eventsRepo.save(oldEvent)).thenReturn(oldEvent);
		when(modelMapper.map(oldEvent, EventsDto.class)).thenReturn(newEvent);

		// Call the method under test
		EventsDto result = eventsService.updateEvent(eventId, newEvent);

		// Verify the results
		assertEquals(newEvent, result);
	}

	@Test
	public void testUpdateEvent_EventNotFound() {
		// Arrange
		int eventId = 1;
		EventsDto newEvent = new EventsDto(1, "kk", "concert", 2000, 500, "chennai","xyz", LocalDate.now(), LocalTime.now(),null);
		when(eventsRepo.findById(1)).thenReturn(Optional.empty());
		String expectedMessage = "Event not found by this Id";
		// Act & Assert
		Exception exception = assertThrows(ResourceNotFoundException.class,
				() -> eventsService.updateEvent(eventId, newEvent));
		String actualMessage = exception.getMessage();
		verify(eventsRepo, times(1)).findById(eventId);
		verify(eventsRepo, never()).save(any(Events.class));
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	public void testFindEventsByCategory() {
		// Create mock data
		int id = 1;
		List<Events> eventsList = new ArrayList<>();
		Categories cat = new Categories(1, "concert", eventsList);
		Events event1 = new Events(1, "sunidhi", "concert", 2000, 500, "chennai","xyz", LocalDate.now(), LocalTime.now(),
				cat);
		Events event2 = new Events(2, "kk", "concert", 2000, 500, "chennai","abc", LocalDate.now(), LocalTime.now(), cat);
		eventsList.add(event1);
		eventsList.add(event2);

		List<EventsDto> eventsListDto = new ArrayList<>();
		EventsDto eventsDto1 = new EventsDto();
		EventsDto eventsDto2 = new EventsDto();
		eventsListDto.add(eventsDto1);
		eventsListDto.add(eventsDto2);

		EventsListByCat expectedEventsListByCat = new EventsListByCat();
		expectedEventsListByCat.setEventsList(eventsListDto);

		// Define the behavior of the mocked dependencies
		when(categoriesRepository.findById(id)).thenReturn(Optional.of(cat));
		when(eventsRepo.findByCategories(cat)).thenReturn(eventsList);
		when(modelMapper.map(event1, EventsDto.class)).thenReturn(eventsDto1);
		when(modelMapper.map(event2, EventsDto.class)).thenReturn(eventsDto2);

		// Call the method under test
		EventsListByCat result = eventsService.findEventsByCategory(id);

		// Verify the results
		assertEquals(expectedEventsListByCat.getEventsList().size(), result.getEventsList().size());
		for (int i = 0; i < result.getEventsList().size(); i++) {
			assertEquals(expectedEventsListByCat.getEventsList().get(i), result.getEventsList().get(i));
		}
	}

	@Test
	public void testFindEventsByCategory_CategoryNotFound() {
		// Arrange
		when(categoriesRepository.findById(anyInt())).thenReturn(Optional.empty());
		// Act and Assert
		String expectedMessage = "Category not Found";
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			eventsService.findEventsByCategory(1);
		});	
		String actualMessage = exception.getMessage();
		assertEquals(expectedMessage, actualMessage);
	}

}
