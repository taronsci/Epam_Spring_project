package com.booky.demo.dto;

//import com.booky.demo.model.BookCondition;
//import com.booky.demo.model.RequestStatus;
//import com.booky.demo.model.TransactionType;

import com.booky.demo.model.Book;


public record BookListingDTO(
    Integer id,

    Integer bookId,
    Book book,

    Integer ownerId,
    String ownerUsername,

    String condition,
    String transaction_type,
    String status,
    Double price,

    Integer rentalDuration
//    LocalDate rentalStartDate
) {}
