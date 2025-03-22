package com.att.tdp.popcorn_palace.domain.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "bookings")

public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID bookingId;

    @ManyToOne
    @JoinColumn(name = "showtimeId")
    private ShowtimeEntity showtime;

    private int seatNumber;
    private String userId;  // Store the userId as a String (or UUID if preferred)



}