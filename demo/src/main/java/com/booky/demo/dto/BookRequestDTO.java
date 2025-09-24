package com.booky.demo.dto;

import com.booky.demo.model.Book;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record BookRequestDTO(
    Integer id,

    String requesterUsername,
    Integer requesterId,
    Integer listingId,
    String status,

    Book book,
    Integer ownerId, //was String

    LocalDateTime createdAt,

    LocalDate rentalStartDate
){}
