package com.eve.events.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eve.events.PayLoad.BookingsDto;
import com.eve.events.PayLoad.BookingsRequestDto;
import com.eve.events.service.BookingService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class BookingController {

	@Autowired
	private BookingService bookingService;
	
	@PostMapping("/user/booking/{userId}")
	public ResponseEntity<BookingsDto> placeBooking(@RequestBody List<BookingsRequestDto> serviceItemReqDtos,@PathVariable int userId){
		log.info("Placing Booking for user with id: {}", userId);
		BookingsDto newOrder = bookingService.placeOrder(serviceItemReqDtos, userId); 
		log.info("Booking done successfully with id: {}", newOrder.getBookingId());
		return new ResponseEntity<BookingsDto>(newOrder, HttpStatus.CREATED);
	}
	
	@GetMapping("/admin/booking/viewall")
	public ResponseEntity<List<BookingsDto>> viewAllBookings() {
		log.info("Fetching all Bookings");	
		List<BookingsDto> listUsers = this.bookingService.getOrderList();
		log.info("Fetched {} Booking", listUsers.size());	
		return new ResponseEntity<List<BookingsDto>>(listUsers, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/user/booking/getbyuser/{userId}")
	public ResponseEntity<List<BookingsDto>> getByUserId(@PathVariable int userId) {
		log.info("Fetching Booking for user with id: {}", userId);
		List<BookingsDto> bookingsDto=bookingService.getByUserId(userId);
		log.info("Fetched {} Booking for user with id: {}", bookingsDto.size(), userId);
		return new ResponseEntity<List<BookingsDto>>(bookingsDto, HttpStatus.ACCEPTED);
	}
}
