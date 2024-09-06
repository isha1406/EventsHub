package com.eve.events.service;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eve.events.Exceptions.ResourceNotFoundException;
import com.eve.events.PayLoad.BookingsDto;
import com.eve.events.PayLoad.BookingsRequestDto;
import com.eve.events.entity.BookingItems;
import com.eve.events.entity.Bookings;
import com.eve.events.entity.Events;
import com.eve.events.entity.Users;
import com.eve.events.repository.BookingRepository;
import com.eve.events.repository.EventsRepository;
import com.eve.events.repository.UsersRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BookingService {
	
	@Autowired
	private BookingRepository bookingRepository;
	@Autowired
	private EventsRepository eventsRepository;
	@Autowired
	private UsersRepository usersRepository;
	

	//private Events e;

	@Autowired
	private ModelMapper modelMapper;
 
	
	public BookingsDto placeOrder(List<BookingsRequestDto> bookingsList, int userId) {
		// TODO Auto-generated method stub
		log.info("Placing order for user id: {}", userId);
		Users user = this.usersRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));	
		Bookings newOrder = new Bookings();
		newOrder.setUser(user);
		newOrder.setBookingStatus("Paid");
		List<BookingItems> orderItemsList = new ArrayList<>();
		Events e;
		for(BookingsRequestDto cartItem : bookingsList) {
			int eventId = cartItem.getEventId();
			int quantity = cartItem.getNoOfTickets();
			e= this.eventsRepository.findById(eventId)
					.orElseThrow(() -> new ResourceNotFoundException("Event Not Found"));
			
			if(e.getQuantity()<quantity)
			{
				throw new ResourceNotFoundException(quantity+" Tickets Not Available for "+ e.getEventName());
			}
			else {
				e.setQuantity(e.getQuantity()-quantity);
				eventsRepository.save(e);
			}
			
			BookingItems orderItem = new BookingItems();
			orderItem.setNoOfTickets(quantity);
			orderItem.setEvents(e);;
			double itemPrice = quantity * e.getPrice();
			orderItem.setBookingEventsPrice(itemPrice);
			orderItemsList.add(orderItem);
			newOrder.setBookingItemsList(orderItemsList);
			orderItem.setBookings(newOrder);
 
		}
		double totalPrice = 0;
		for(BookingItems o : orderItemsList) {
			totalPrice += o.getBookingEventsPrice();
		}
		newOrder.setTotalAmount(totalPrice);
		this.bookingRepository.save(newOrder);
		log.info("Order placed successfully for user id: {}", userId);
		return toDto(newOrder);
	}
 

	public List<BookingsDto> getOrderList() {
		log.info("Getting all orders");
		List<Bookings> listBookings= bookingRepository.findAll();
		if(listBookings.size()==0)
			throw new ResourceNotFoundException("Bookings list is empty");
		List<BookingsDto> allUserBookingListDto = listBookings.stream().map(l -> this.toDto(l)).collect(Collectors.toList());
		log.info("Successfully retrieved all orders");
		return allUserBookingListDto;
	}
 
	
	public List<BookingsDto> getByUserId(int userId) {
		log.info("Getting orders for user id: {}", userId);
		Users user = usersRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found by this Id"));
		List<Bookings> bookings=user.getBookings();
		if(bookings.size()==0)
			throw new ResourceNotFoundException("No Booking has been done by User");
		List<BookingsDto> UserBookingListDto = bookings.stream().map(l -> this.toDto(l)).collect(Collectors.toList());
		log.info("Successfully retrieved orders for user id: {}", userId);
		return UserBookingListDto;
	}
	

 
	public BookingsDto toDto(Bookings b) {
		return this.modelMapper.map(b, BookingsDto.class);
	}

	public Bookings toEntity(BookingsDto dto) {
		return this.modelMapper.map(dto, Bookings.class);
	}

 
}
