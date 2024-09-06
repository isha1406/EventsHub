package com.eve.events.PayLoad;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequest {

	private int eventId;
//	private int quantity;
	private int noOfTickets;
}
