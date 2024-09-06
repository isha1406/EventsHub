package com.eve.events.PayLoad;





import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingsRequestDto {

	private int eventId;
	private int noOfTickets;
//	private int quantity;	
}
