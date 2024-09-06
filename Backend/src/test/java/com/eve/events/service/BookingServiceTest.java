package com.eve.events.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import com.eve.events.Exceptions.ResourceNotFoundException;
import com.eve.events.PayLoad.BookingItemsDto;
import com.eve.events.PayLoad.BookingsDto;
import com.eve.events.PayLoad.EventsDto;
import com.eve.events.PayLoad.UserDto;
import com.eve.events.entity.BookingItems;
import com.eve.events.entity.Bookings;
import com.eve.events.entity.Events;
import com.eve.events.entity.Role;
import com.eve.events.entity.Users;
import com.eve.events.repository.BookingRepository;
import com.eve.events.repository.EventsRepository;
import com.eve.events.repository.UsersRepository;

@SpringBootTest
public class BookingServiceTest {

	@InjectMocks
	private BookingService bookingService;

	@Mock
	private BookingRepository bookingRepository;
	@Mock
	private EventsRepository eventsRepository;
	@Mock
	private UsersRepository usersRepository;
	@Mock
	private ModelMapper modelMapper;

	@Test
	public void testGetByUserId_forValidUserId() {
		// Arrange
		int userId = 1;
		Users user = new Users(1, "abc@gmail.com", "Fred", "Weasely", "8976543765", "abc", Role.USER, null);
		UserDto userDto = new UserDto(1, "abc@gmail.com", "Fred", "Weasely", "8976543765", "abc", Role.USER);

		Events event = new Events(1, "sunidhi", "concert", 2000.00, 500, "chennai", "xyz",LocalDate.now(), LocalTime.now(),
				null);
		EventsDto eventsDto = new EventsDto(1, "sunidhi", "concert", 2000.00, 500, "chennai","xyz", LocalDate.now(),
				LocalTime.now(),null);

		Events event2 = new Events(2, "kk", "concert", 2000.00, 500, "chennai","xyz", LocalDate.now(), LocalTime.now(), null);
		EventsDto events2Dto = new EventsDto(2, "kk", "concert", 2000.00, 500, "chennai","xyz", LocalDate.now(),
				LocalTime.now(),null);

		BookingItems bookingItems1 = new BookingItems(1, 2, event.getPrice(), event, null);
		List<BookingItems> bookingsList1 = Arrays.asList(bookingItems1);

		double amount1 = 0.0;
		for (BookingItems b : bookingsList1) {
			amount1 += b.getEvents().getPrice();
		}

		BookingItems bookingItems2 = new BookingItems(1, 5, event2.getPrice(), event2, null);
		List<BookingItems> bookingsList2 = Arrays.asList(bookingItems2);

		double amount2 = 0.0;
		for (BookingItems b : bookingsList2) {
			amount2 += b.getEvents().getPrice();
		}

		Bookings booking1 = new Bookings(1, user, bookingsList1, amount1, "paid");
		Bookings booking2 = new Bookings(2, user, bookingsList2, amount2, "paid");
		user.setBookings(Arrays.asList(booking1, booking2));

		when(usersRepository.findById(userId)).thenReturn(Optional.of(user));

		BookingItemsDto bookingItemsDto1 = new BookingItemsDto(1, 2, event.getPrice(), eventsDto);
		List<BookingItemsDto> bookingsListDto1 = Arrays.asList(bookingItemsDto1);

		BookingItemsDto bookingItemsDto2 = new BookingItemsDto(2, 2, event2.getPrice(), events2Dto);
		List<BookingItemsDto> bookingsListDto2 = Arrays.asList(bookingItemsDto2);

		BookingsDto dto1 = new BookingsDto(1, userDto, bookingsListDto1, amount1, "paid");
		BookingsDto dto2 = new BookingsDto(2, userDto, bookingsListDto2, amount1, "paid");
		when(modelMapper.map(booking1, BookingsDto.class)).thenReturn(dto1);
		when(modelMapper.map(booking2, BookingsDto.class)).thenReturn(dto2);

		// Act
		List<BookingsDto> result = bookingService.getByUserId(userId);

		// Assert
		assertEquals(2, result.size());
		assertTrue(result.contains(dto1));
		assertTrue(result.contains(dto2));
	}

    @Test
    public void testGetByUserId_forInvalidUserId() {
		
        String expectedMessage = "User not found";
        when(bookingRepository.findById(anyInt())).thenReturn(Optional.empty());
        
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
        	bookingService.getByUserId(1);
        });
        String actualMessage = exception.getMessage();
        // Assert
		assertTrue(actualMessage.contains(expectedMessage));
    
}


	@Test
	public void testGetOrderList() {
		// Arrange
		Users user = new Users(1, "abc@gmail.com", "Fred", "Weasely", "8976543765", "abc", Role.USER, null);
		UserDto userDto = new UserDto(1, "abc@gmail.com", "Fred", "Weasely", "8976543765", "abc", Role.USER);

		Events event = new Events(1, "sunidhi", "concert", 2000.00, 500, "chennai","xyz", LocalDate.now(), LocalTime.now(),
				null);
		EventsDto eventsDto = new EventsDto(1, "sunidhi", "concert", 2000.00, 500, "chennai","xyz", LocalDate.now(),
				LocalTime.now(),null);

		Events event2 = new Events(2, "kk", "concert", 2000.00, 500, "chennai","xyz", LocalDate.now(), LocalTime.now(), null);
		EventsDto events2Dto = new EventsDto(2, "kk", "concert", 2000.00, 500, "chennai","xyz", LocalDate.now(),
				LocalTime.now(),null);

		BookingItems bookingItems1 = new BookingItems(1, 2, event.getPrice(), event, null);
		List<BookingItems> bookingsList1 = Arrays.asList(bookingItems1);

		double amount1 = 0.0;
		for (BookingItems b : bookingsList1) {
			amount1 += b.getEvents().getPrice();
		}

		BookingItems bookingItems2 = new BookingItems(1, 5, event2.getPrice(), event2, null);
		List<BookingItems> bookingsList2 = Arrays.asList(bookingItems2);

		double amount2 = 0.0;
		for (BookingItems b : bookingsList2) {
			amount2 += b.getEvents().getPrice();
		}

		BookingItemsDto bookingItemsDto1 = new BookingItemsDto(1, 2, event.getPrice(), eventsDto);
		List<BookingItemsDto> bookingsListDto1 = Arrays.asList(bookingItemsDto1);

		BookingItemsDto bookingItemsDto2 = new BookingItemsDto(2, 2, event2.getPrice(), events2Dto);
		List<BookingItemsDto> bookingsListDto2 = Arrays.asList(bookingItemsDto2);

		Bookings booking1 = new Bookings(1, user, bookingsList1, amount1, "paid");
		Bookings booking2 = new Bookings(2, user, bookingsList2, amount2, "paid");
		when(bookingRepository.findAll()).thenReturn(Arrays.asList(booking1, booking2));

		BookingsDto dto1 = new BookingsDto(1, userDto, bookingsListDto1, amount1, "paid");
		BookingsDto dto2 = new BookingsDto(2, userDto, bookingsListDto2, amount1, "paid");
		when(modelMapper.map(booking1, BookingsDto.class)).thenReturn(dto1);
		when(modelMapper.map(booking2, BookingsDto.class)).thenReturn(dto2);

		// Act
		List<BookingsDto> result = bookingService.getOrderList();

		// Assert
		assertEquals(2, result.size());
		assertSame(dto1, result.get(0));
		assertSame(dto2, result.get(1));
	}

//	@Test
//	public void testGetOrderList_ForEmptyList() {
//		// Arrange
//		Bookings booking1 = new Bookings();
//		//Bookings booking2 = new Bookings();
//		when(bookingRepository.findAll()).thenReturn(Arrays.asList(booking1));
//		// Assert
//		String expectedMessage = "Bookings list is empty";
//		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
//			bookingService.getOrderList();
//		});
//		String actualMessage = exception.getMessage();
//		assertTrue(actualMessage.contains(expectedMessage));
//	}

//	@Test
//	public void testPlaceOrder() {
//		// Arrange
//		int userId = 1;
//		Users user = new Users(1, "abc@gmail.com", "Fred", "Weasely", "8976543765", "abc", Role.USER, null);
//		UserDto userDto = new UserDto(1, "abc@gmail.com", "Fred", "Weasely", "8976543765", "abc", Role.USER);
////		Users user=new Users();
////		UserDto userDto=new UserDto();
//		when(usersRepository.findById(userId)).thenReturn(Optional.of(user));
//
//		int eventId = 1;
//		Events event = new Events(1, "sunidhi", "concert", 2000.00, 500, "chennai", LocalDate.now(), LocalTime.now(),
//				null);
//		EventsDto eventsDto = new EventsDto(1, "sunidhi", "concert", 2000.00, 500, "chennai", LocalDate.now(),
//				LocalTime.now());
//		when(eventsRepository.findById(eventId)).thenReturn(Optional.of(event));
//
//		BookingsRequestDto requestDto = new BookingsRequestDto(1, 5);
//		List<BookingsRequestDto> bookingsList = Arrays.asList(requestDto);
//
//		BookingItems bookingItems1 = new BookingItems(1, requestDto.getQuantity(), event.getPrice(), event, null);
//		List<BookingItems> bookingItemsList = new ArrayList<>();
//		bookingItemsList.add(bookingItems1);
//		double amount = 0.0;
//		for (BookingItems item : bookingItemsList) {
//			amount += item.getEvents().getPrice();
//		}
//
//		BookingItemsDto bookingItemsDto1 = new BookingItemsDto(1, requestDto.getQuantity(), event.getPrice(),
//				eventsDto);
//		List<BookingItemsDto> bookingItemsListDto = new ArrayList<>();
//		bookingItemsListDto.add(bookingItemsDto1);
//
//		Bookings expectedOrder = new Bookings(1, user, bookingItemsList, amount, "paid");
//		when(bookingRepository.save(any())).thenReturn(expectedOrder);
//
//		BookingsDto expectedDto = new BookingsDto(1, userDto, bookingItemsListDto, amount, "paid");
//		System.out.println(expectedDto);
//		when(modelMapper.map(expectedOrder, BookingsDto.class)).thenReturn(expectedDto);
//
//		// Act
//		BookingsDto result = bookingService.placeOrder(bookingsList, userId);
//
//		// Assert
//		assertSame(expectedDto, result);
//		verify(eventsRepository).save(event);
//		assertEquals(5, event.getQuantity());
//	}

}
