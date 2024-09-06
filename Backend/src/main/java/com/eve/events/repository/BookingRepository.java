package com.eve.events.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eve.events.entity.Bookings;

public interface BookingRepository extends JpaRepository<Bookings, Integer>{

}
