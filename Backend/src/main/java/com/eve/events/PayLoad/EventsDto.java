package com.eve.events.PayLoad;

import java.time.LocalDate;

import java.time.LocalTime;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventsDto {

	
	 private int eventId;
	 private String eventName;
	 private String description;
	 private double price;
	 private int quantity;
	 private String location;
	 private String image;
	 private LocalDate date;	 
	 private LocalTime time;
	 private CategoriesDto categories;
	 
	
}
