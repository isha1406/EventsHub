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

import com.eve.events.PayLoad.EventsDto;
import com.eve.events.PayLoad.EventsListByCat;
import com.eve.events.service.EventsService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/api")
//@CrossOrigin("*")
public class EventsController {

	@Autowired
	private EventsService eventsService;

	@PostMapping("/admin/events/create/{catid}")
	public ResponseEntity<EventsDto> createEvent(@RequestBody EventsDto event, @PathVariable int catid) {
		log.info("Creating event with id: {}", event.getEventId());
		EventsDto createdEvent = eventsService.createEvent(event, catid);
		log.info("Event created successfully with id: {}", createdEvent.getEventId());
		return new ResponseEntity<EventsDto>(createdEvent, HttpStatus.CREATED);
	}

	@GetMapping("events/viewall")
	public ResponseEntity<List<EventsDto>> viewAllEvents() {
		log.info("Fetching all events");
		List<EventsDto> listEvents = this.eventsService.getAllEvnets();
		log.info("Fetched {} events", listEvents.size());
		return new ResponseEntity<List<EventsDto>>(listEvents, HttpStatus.ACCEPTED);
	}

	@PutMapping("admin/events/update/{eventId}")
	public ResponseEntity<EventsDto> updateEvent(@PathVariable int eventId, @RequestBody EventsDto eventsDto) {
		log.info("Updating event with id: {}", eventId);
		EventsDto updatedEvent = this.eventsService.updateEvent(eventId, eventsDto);
		log.info("Event updated successfully with id: {}", eventId);
		return new ResponseEntity<EventsDto>(updatedEvent, HttpStatus.CREATED);
	}

	@DeleteMapping("admin/events/delete/{eventId}")
	public ResponseEntity<String> deleteProduct(@PathVariable int eventId) {
		log.info("Deleting event with id: {}", eventId);
		eventsService.deleteEvent(eventId);
		log.info("Event deleted successfully with id: {}", eventId);
		return new ResponseEntity<String>("Event Deleted", HttpStatus.OK);
	}

	@GetMapping("/events/getevents/{id}")
	public ResponseEntity<EventsListByCat> viewAllEventsByCategory(@PathVariable int id) {
		log.info("Fetching event with id: {}", id);
		EventsListByCat listOfEventsListByCats = this.eventsService.findEventsByCategory(id);
		log.info("Event fetched successfully with id: {}", id);
		return new ResponseEntity<EventsListByCat>(listOfEventsListByCats, HttpStatus.OK);
	}
	
	@GetMapping("/admin/events/pastevents")
	public ResponseEntity<List<EventsDto>> viewAllPastEvents() {
		log.info("Fetching all PastEvents");
		List<EventsDto> listPastEvents = this.eventsService.pastEvents();
		log.info("Fetched {} PastEvents", listPastEvents.size());
		return new ResponseEntity<List<EventsDto>>(listPastEvents, HttpStatus.OK);
	}
	
	@GetMapping("/events/upcomingevents")
	public ResponseEntity<List<EventsDto>> viewAllUpComingEvents() {
		log.info("Fetching all UpcomingEvents");
		List<EventsDto> listUpcomingEvents = this.eventsService.upComingEvents();
		log.info("Fetched {} UpcomingEvents", listUpcomingEvents.size());
		return new ResponseEntity<List<EventsDto>>(listUpcomingEvents, HttpStatus.OK);
	}
}
