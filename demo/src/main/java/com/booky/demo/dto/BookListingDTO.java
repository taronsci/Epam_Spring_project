package com.booky.demo.dto;

import com.booky.demo.model.Book;

public record BookListingDTO(
    Integer id,

    Integer bookId,
    Book book,

    Integer ownerId,
    String ownerUsername,

    String condition,
    String transactionType,
    String status,
    Double price,

    Integer rentalDuration
) {}
