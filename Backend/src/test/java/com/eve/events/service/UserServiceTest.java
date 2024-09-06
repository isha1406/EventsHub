package com.eve.events.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.eve.events.Exceptions.ResourceNotFoundException;
import com.eve.events.PayLoad.UserDto;
import com.eve.events.entity.Role;
import com.eve.events.entity.Users;
import com.eve.events.repository.UsersRepository;



@SpringBootTest
public class UserServiceTest {
	
    @InjectMocks
    UserService userService;

    @Mock
    UsersRepository usersRepository;

    @Mock
    ModelMapper modelMapper;
    
    @Mock
    PasswordEncoder passwordEncoder;

    private UserDto userDto;
    private Users user;
    

    @BeforeEach
    public void setUp() {
        userDto = new UserDto(1, "abc@gmail.com", "Fred", "Weasely", "8976543765", "abc", Role.USER);
        user = new Users(1, "abc@gmail.com", "Fred", "Weasely", "8976543765", "abc", Role.USER, null);
       
        when(modelMapper.map(userDto, Users.class)).thenReturn(user);
        when(modelMapper.map(user, UserDto.class)).thenReturn(userDto);

    }

//    @Test
//    public void testCreateUser() {
//        when(usersRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.empty());
//        when(usersRepository.save(any(Users.class))).thenAnswer(i -> i.getArguments()[0]);
//
//        UserDto savedUserDto = userService.createUser(userDto);
//
//        assertEquals(userDto.getEmail(), savedUserDto.getEmail());
//        assertEquals(userDto.getFirstName(), savedUserDto.getFirstName());
//        assertEquals(userDto.getLastName(), savedUserDto.getLastName());
//        assertEquals(userDto.getMobileNumber(), savedUserDto.getMobileNumber());
//        assertEquals(userDto.getPassword(), savedUserDto.getPassword());
//    }
//    
    @Test
    public void testGetAllUsers() {
        when(usersRepository.findAll()).thenReturn(Arrays.asList(user));

        List<UserDto> allUserDto = userService.getAllUsers();

        assertEquals(1, allUserDto.size());
        assertEquals(userDto.getEmail(), allUserDto.get(0).getEmail());
        assertEquals(userDto.getFirstName(), allUserDto.get(0).getFirstName());
        assertEquals(userDto.getLastName(), allUserDto.get(0).getLastName());
        assertEquals(userDto.getMobileNumber(), allUserDto.get(0).getMobileNumber());
        assertEquals(userDto.getPassword(), allUserDto.get(0).getPassword());
    }
    
    @Test
    public void testGetAllUsers_ForEmptyList() {
    	String expectedMessage = "Users list is empty";
    	List<Users>usersList=new ArrayList<>();
        //when(usersRepository.findAll()).thenReturn(Arrays.asList(user));
    	when(usersRepository.findAll()).thenReturn(usersList);
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
        	userService.getAllUsers();
        });
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
    
    @Test
    public void testDeleteUser_forValidUser() {
        when(usersRepository.findById(userDto.getUserId())).thenReturn(Optional.of(user));

        userService.deleteUser(userDto.getUserId());

        verify(usersRepository, times(1)).delete(user);
    }
    
    @Test
	public void testDeleteUser_forInvalidUser() {
 
		String expectedMessage = "User not Found";
		when(usersRepository.findById(1)).thenReturn(Optional.empty());
 
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
        	userService.deleteUser(1);
        });
		String actualMessage = exception.getMessage();
		verify(usersRepository, never()).delete(user);
//		verify(userRepository).delete(actualUser);
		 assertTrue(actualMessage.contains(expectedMessage));
 
	}
    
    @Test
    public void testGetUserByUserId_whenUserIdFound() {
        when(usersRepository.findById(1)).thenReturn(Optional.of(user));

        UserDto result = userService.getUserByUserId(userDto.getUserId());
        when(modelMapper.map(user, UserDto.class)).thenReturn(userDto);
        assertEquals(userDto, result);
        verify(usersRepository, times(1)).findById(userDto.getUserId());
    }
    
    @Test
    public void testGetUserByUserId_whenUserIdNotFound() {

        String expectedMessage = "User not Found";
 
        when(usersRepository.findById(anyInt())).thenReturn(Optional.empty());
        when(modelMapper.map(user, UserDto.class)).thenReturn(userDto);
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
        	userService.getUserByUserId(1);
        });
        String actualMessage = exception.getMessage();
        verify(usersRepository, never()).save(user);
 
        assertEquals(expectedMessage, actualMessage);
    }


    @Test
    public void testUpdateUser() {
        UserDto newUser = new UserDto();
        newUser.setEmail("test@test.com");
        newUser.setFirstName("Test");
        newUser.setLastName("User");
        newUser.setMobileNumber("1234567890");
        newUser.setPassword(passwordEncoder.encode("password"));

        when(usersRepository.findById(1)).thenReturn(Optional.of(user));
        when(usersRepository.save(user)).thenReturn(user);
        when(modelMapper.map(user, UserDto.class)).thenReturn(newUser);

        UserDto updatedUser = userService.updateUser(1, newUser);

        assertEquals(newUser.getEmail(), updatedUser.getEmail());
        assertEquals(newUser.getFirstName(), updatedUser.getFirstName());
        assertEquals(newUser.getLastName(), updatedUser.getLastName());
        assertEquals(newUser.getMobileNumber(), updatedUser.getMobileNumber());
    
}
    
   
	

}
