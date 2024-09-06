package com.eve.events.PayLoad;


import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingsDto {
	
	private int bookingId;
	private UserDto user;
	private List<BookingItemsDto> bookingItemsList = new ArrayList<>();
	private Double totalAmount;
	private String bookingStatus;
	
	
}
