package com.eve.events.PayLoad;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingItemsDto {
	
	private int bookingItemId;
//	private int quantity;
	private int noOfTickets;
	private double bookingEventsPrice;
	private EventsDto events;
}
