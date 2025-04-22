package com.booking.bookingapp.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @ToString

@Entity
@Table(name = "confirmed_seats") 
public class ConfirmedSeatEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", nullable = false, length = 100)
    private String userName;

    @Column(name = "seat_id", nullable = false)
    private Long seatId;

    @Column(name = "booked_at", nullable = false)
    private LocalDateTime bookedAt;

    @Column(name = "payment", nullable = false)
    private Double payment;
}
