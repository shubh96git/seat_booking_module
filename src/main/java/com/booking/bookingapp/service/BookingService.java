package com.booking.bookingapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.bookingapp.concurrency.SeatManager;

@Service
public class BookingService {

    @Autowired
    private SeatManager seatLockManager;

    
    public boolean lockSeat(Long seatId, String userName) throws InterruptedException {
        return seatLockManager.getSeatForThread(seatId, userName);
    }

    public boolean cancelBooking(Long seatId, String userName) {
        return seatLockManager.unlockTheSeat(seatId, userName);
    }

    public boolean confirmBooking(Long seatId, String userName) {
        return seatLockManager.unlockTheSeat(seatId, userName);
    }
}
