package com.eve.events.entity;

import java.time.LocalDate;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

//import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
//import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "events")
public class Events {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "event_id")
	private int eventId;
	private String eventName;
	private String description;
	private double price;
	private int quantity;
	private String location;
	private String image;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;

	@JsonFormat(pattern = "HH:mm:ss")
	private LocalTime time;

	@ManyToOne()
	private Categories categories;

}
