package com.booking.bookingapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.booking.bookingapp.service.BookingService;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/select_seat")
    public ResponseEntity<String> lockSeat(@RequestParam Long seatId, @RequestParam String userName) throws InterruptedException {

        // collect the userName from the JSON token
        // String userName = "";
        boolean locked = bookingService.lockSeat(seatId, userName);
        return locked ? ResponseEntity.ok("Seat locked") : ResponseEntity.status(HttpStatus.CONFLICT).body("Seat already locked");
    }

    @PostMapping("/cancel")
    public ResponseEntity<String> cancelBooking(@RequestParam Long seatId, @RequestParam String userName) {
        bookingService.cancelBooking(seatId, userName);
        return ResponseEntity.ok("Booking canceled and seat unlocked");
    }

    @PostMapping("/confirm")
    public ResponseEntity<String> confirmBooking(@RequestParam Long seatId, @RequestParam String userName) {
        
        boolean locked = bookingService.confirmBooking(seatId, userName);
        return locked ? ResponseEntity.ok("Booking confirmed") : ResponseEntity.status(HttpStatus.CONFLICT).body("Booking confirmation failed");
    }
}