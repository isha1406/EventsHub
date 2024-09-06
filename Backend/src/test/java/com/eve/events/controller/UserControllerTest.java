package com.eve.events.controller;


import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.eve.events.PayLoad.UserDto;
import com.eve.events.entity.Role;
import com.eve.events.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService userService;
	private static ObjectMapper objectMapper;

	public String mapToJson(Object object) throws JsonProcessingException {
		objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}

	private final UserDto USER_DTO = new UserDto(1, "abc@gmail.com", "Fred", "Weasely", "8976543765", "abc", Role.USER);



//	@Test
//	public void testCreateUser() throws Exception {
//		when(userService.createUser(USER_DTO)).thenReturn(USER_DTO);
//		mockMvc.perform(MockMvcRequestBuilders.post("/api/users/create")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(mapToJson(USER_DTO))
//				.accept(MediaType.APPLICATION_JSON))
//		.andExpect(MockMvcResultMatchers.status().isCreated())
//		.andExpect(MockMvcResultMatchers.jsonPath("$").value(USER_DTO));
//	}
	
	@Test	
//	@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void testViewAllUsers() throws Exception {
		List<UserDto> allUsersDtos = Arrays.asList(USER_DTO);
		when(userService.getAllUsers()).thenReturn(allUsersDtos);
		mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/users/viewall").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isAccepted())
				.andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(1));
	}

	@Test
	//@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void testUpdateUser() throws Exception {
		UserDto newUserDto = new UserDto(1, "xyz@gmail.com", "Ron", "Weasely", "8976548765", "xyz", Role.USER);
		when(userService.updateUser(1, USER_DTO)).thenReturn(newUserDto);
		mockMvc.perform(MockMvcRequestBuilders.put("/api/user/users/update/{userId}", 1)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapToJson(USER_DTO))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isCreated());
	}

	@Test
	//@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void testDeleteUser() throws Exception {
		doNothing().when(userService).deleteUser(1);
		mockMvc.perform(
				MockMvcRequestBuilders.delete("/api/admin/users/delete/{userId}", 1).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	//@WithMockUser(username="admin",roles={"USER","ADMIN"})
	public void testGetByUserId() throws Exception {
		when(userService.getUserByUserId(1)).thenReturn(USER_DTO);
		mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/users/getbyid/{userId}", 1).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
				//.andExpect(MockMvcResultMatchers.jsonPath("$").value(USER_DTO));
	}
}
