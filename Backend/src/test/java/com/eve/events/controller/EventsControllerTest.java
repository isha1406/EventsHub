package com.eve.events.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.eve.events.PayLoad.EventsDto;
import com.eve.events.service.EventsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class EventsControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private EventsService eventsService;
	private static ObjectMapper objectMapper;

	public String mapToJson(Object object) throws JsonProcessingException {
		return objectMapper.writeValueAsString(object);
	}

	private final EventsDto EVENTS_DTO = new EventsDto(1, "sunidhi", "concert", 2000, 500, "chennai","xyz", LocalDate.now(),
			LocalTime.now(),null);

	@BeforeAll
	public static void setUp() {
		objectMapper = new ObjectMapper();
	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	public void testCreateEvents() throws JsonProcessingException, Exception {
		when(eventsService.createEvent(EVENTS_DTO, 1)).thenReturn(EVENTS_DTO);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/admin/events/create").contentType(MediaType.APPLICATION_JSON)
				.content(mapToJson(EVENTS_DTO)).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$").value(EVENTS_DTO));
	}

	@Test
	//@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	public void testViewAllEvents() throws Exception {
		List<EventsDto> allUsersDtos = Arrays.asList(EVENTS_DTO);
		when(eventsService.getAllEvnets()).thenReturn(allUsersDtos);
		mockMvc.perform(MockMvcRequestBuilders.get("/api/events/viewall").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isAccepted())
				.andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(1));
	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	public void testUpdateEvent() throws Exception {
		EventsDto newEventsDto = new EventsDto(1, "sunidhi", "concert", 2000, 500, "chennai","xyz", LocalDate.now(),
				LocalTime.now(),null);
		when(eventsService.updateEvent(1, EVENTS_DTO)).thenReturn(newEventsDto);
		mockMvc.perform(MockMvcRequestBuilders.put("/api/admin/events/update/{eventId}", 1)
				.contentType(MediaType.APPLICATION_JSON).content(mapToJson(EVENTS_DTO))
				.accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.serviceId").value(1));
	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	public void testDeleteEvent() throws Exception {
		doNothing().when(eventsService).deleteEvent(1);
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/admin/events/delete/{eventId}", 1)
				.accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
	}

//    @Test
//    public void testFindByServiceId() throws Exception {
//    	when(eventsService.).thenReturn(EVENTS_DTO);
//		mockMvc.perform(MockMvcRequestBuilders.get("/api/services/findbyserviceid/{serviceId}",1)
//				.accept(MediaType.APPLICATION_JSON))
//		.andExpect(MockMvcResultMatchers.status().isOk())
//		.andExpect(MockMvcResultMatchers.jsonPath("$.serviceId").value(1));
//    }

}
