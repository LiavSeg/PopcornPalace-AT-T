package com.att.tdp.popcorn_palace.domain.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

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

    @NotNull(message = "Showtime ID cannot be null")
    private Integer showtimeId;

    @Positive(message = "Seat number must be a positive integer")
    private int seatNumber;

    @NotNull(message = "User ID cannot be null")
    @Size(max = 36, message = "UUID string must not exceed 36 characters")
    private String userId;
}