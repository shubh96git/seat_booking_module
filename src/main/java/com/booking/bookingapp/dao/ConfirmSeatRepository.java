package com.booking.bookingapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.booking.bookingapp.entity.ConfirmedSeatEntity;

public interface ConfirmSeatRepository extends JpaRepository<ConfirmedSeatEntity, Long> {
    
}
