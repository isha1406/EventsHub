package com.eve.events.entity;




import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "booking_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingItems {
 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int bookingItemId;
	private int noOfTickets;
	//private int quantity;
	private double bookingEventsPrice;
	
	@ManyToOne
	private Events events;
	
	@ManyToOne
	private Bookings bookings;
  
}
