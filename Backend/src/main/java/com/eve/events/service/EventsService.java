package com.eve.events.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eve.events.Exceptions.ResourceNotFoundException;
import com.eve.events.PayLoad.EventsDto;
import com.eve.events.PayLoad.EventsListByCat;
import com.eve.events.entity.Categories;
import com.eve.events.entity.Events;
import com.eve.events.repository.CategoriesRepository;
import com.eve.events.repository.EventsRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EventsService {

	@Autowired
	private EventsRepository eventsRepo;

	@Autowired
	private CategoriesRepository categoriesRepository;

	@Autowired
	private ModelMapper modelMapper;

	public EventsDto createEvent(EventsDto eventsDto, int catId) {
		log.info("Creating event with category id: {}", catId);
		Categories cat = categoriesRepository.findById(catId)
				.orElseThrow(() -> new ResourceNotFoundException("This Category id not found Catgory"));

		Events events = this.toEntity(eventsDto);

		Optional<Events> check1 = eventsRepo.findById(events.getEventId());
		Optional<Events> check2 = eventsRepo.findByEventName(events.getEventName());

		if (check1.isPresent() || check2.isPresent()) {
			log.error("Event already exists");
			throw new ResourceNotFoundException("Event already exists");
		}

		events.setCategories(cat);
		Events savedEvent = this.eventsRepo.save(events);
		EventsDto savEveDto = this.toDto(savedEvent);
		log.info("Event created successfully with id: {}", savedEvent.getEventId());
		return savEveDto;
	}

	public List<EventsDto> getAllEvnets() {
		log.info("Fetching all events");
		List<Events> allEvents = eventsRepo.findAll();
		if (allEvents.size() == 0)
			throw new ResourceNotFoundException("Events list is empty");
		List<EventsDto> allEventsDto = allEvents.stream().map(event -> this.toDto(event)).collect(Collectors.toList());
		log.info("Fetched {} events", allEventsDto.size());
		return allEventsDto;
	}

	//updateEvents
	public EventsDto updateEvent(int eventId, EventsDto newEvent) {
		log.info("Updating event with id: {}", eventId);
		Events oldEvent = eventsRepo.findById(eventId)
				.orElseThrow(() -> new ResourceNotFoundException("Event not found by this Id"));
//		Optional<Events> existEvents=eventsRepo.findByEventName(newEvent.getEventName());
//		if(existEvents!=null) {
//			throw new ResourceNotFoundException("Event already exits");
//		}
        oldEvent.setEventName(newEvent.getEventName());
	//	oldEvent.setEventName(eventNameString);
		oldEvent.setDescription(newEvent.getDescription());
		oldEvent.setPrice(newEvent.getPrice());
		oldEvent.setQuantity(newEvent.getQuantity());
		// oldEvent.setImage(newEvent.getImage());
		oldEvent.setImage(newEvent.getImage());
		oldEvent.setLocation(newEvent.getLocation());
		oldEvent.setDate(newEvent.getDate());
		oldEvent.setTime(newEvent.getTime());

		Events saveEvent = this.eventsRepo.save(oldEvent);
		EventsDto upEvnetsDto = this.toDto(saveEvent);
		log.info("Event updated successfully with id: {}", saveEvent.getEventId());
		return upEvnetsDto;
	}

	public void deleteEvent(int eventId) {

		log.info("Deleting event with id: {}", eventId);
		Events byId = this.eventsRepo.findById(eventId)
				.orElseThrow(() -> new ResourceNotFoundException("Event not Found"));

		this.eventsRepo.delete(byId);
		log.info("Event deleted successfully with id: {}", eventId);
	}

	public EventsListByCat findEventsByCategory(int id) {
		log.info("Fetching events by category id: {}", id);
		Categories category = categoriesRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category not Found"));
		List<Events> eventsList = eventsRepo.findByCategories(category);
		if (eventsList.size() == 0)
			throw new ResourceNotFoundException("Events list is empty");
		List<EventsDto> eventsListDto = eventsList.stream().map(e -> toDto(e)).collect(Collectors.toList());
		EventsListByCat eventsListByCat = new EventsListByCat();
		eventsListByCat.setEventsList(eventsListDto);
		log.info("Fetched {} events by category id: {}", id);
		return eventsListByCat;
	}
	
	public List<EventsDto> pastEvents(){
		log.info("Fetching all pastEvents");
		LocalDate date=LocalDate.now();
		//LocalTime time=LocalTime.now();
		List<Events> EventsList = eventsRepo.findAll();
		List<Events> pastEvents=new ArrayList<>();
		if (EventsList.size() == 0)
			throw new ResourceNotFoundException("Events list is empty");
		else {
			for(Events e:EventsList)
			{
				if(e.getDate().isBefore(date))
				{
					pastEvents.add(e);
				}
			}
		}
		
		if(pastEvents.size()==0)
			throw new ResourceNotFoundException("Past Events list is empty");
		List<EventsDto> pastEventsDto = pastEvents.stream().map(event -> this.toDto(event)).collect(Collectors.toList());
		log.info("Fetched {} pastEvents", pastEventsDto.size());
		return pastEventsDto;
	}
	
	public List<EventsDto> upComingEvents(){
		log.info("Fetching all upComingEvents");
		LocalDate date=LocalDate.now();
		//LocalTime time=LocalTime.now();
		List<Events> EventsList = eventsRepo.findAll();
		List<Events> upComingEvents=new ArrayList<>();
		if (EventsList.size() == 0)
			throw new ResourceNotFoundException("Events list is empty");
		else {
			for(Events e:EventsList)
			{
				if(e.getDate().isAfter(date))
				{
					upComingEvents.add(e);
				}
			}
		}
		if(upComingEvents.size()==0)
			throw new ResourceNotFoundException("UpComing Events list is empty");
		List<EventsDto> upComingEventsDto =upComingEvents.stream().map(event -> this.toDto(event)).collect(Collectors.toList());
		log.info("Fetched {} upComingEvents", upComingEventsDto.size());
		return upComingEventsDto;
	}


	public Events toEntity(EventsDto eventsDto) {
		Events events = this.modelMapper.map(eventsDto, Events.class);
		return events;
	}

	public EventsDto toDto(Events events) {
		EventsDto eventsDto = this.modelMapper.map(events, EventsDto.class);
		return eventsDto;
	}

}
