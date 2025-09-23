package com.booky.demo.dto;

import com.booky.demo.model.Book;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record BookRequestDTO(
    Integer id,
    Integer requesterId,
    Integer listingId,
    String status,

    Book book,
    String ownerId,

    LocalDateTime requestAt,

    LocalDate rentalStartDate
){}
