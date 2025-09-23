package com.booky.demo.service;

import com.booky.demo.dao.BookDAO;
import com.booky.demo.dao.BookListingRepository;
import com.booky.demo.dao.UserDAO;
import com.booky.demo.dto.BookListingDTO;
import com.booky.demo.model.*;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class BookListingService {

    private final BookDAO bookDAO;
    private final UserDAO userDAO;

    private final BookListingRepository bookListingRepository;

    public BookListingService(BookDAO bookbookDAO,BookListingRepository repository, UserDAO userDAO) {
        this.bookDAO = bookbookDAO;
        this.bookListingRepository = repository;
        this.userDAO = userDAO;
    }

    @Transactional
    public Integer createListing(BookListingDTO dto) {
        System.out.println("creating listing");
        BookListing listing = new BookListing();
        listing.setBook_id(dto.bookId());
        listing.setOwner_id(dto.ownerId());
        listing.setCondition(dto.condition());
        listing.setTransaction_type(dto.transaction_type());
        listing.setStatus("PENDING");

        BookListing saved = bookListingRepository.save(listing);    //maybe reassign to listing
        System.out.println("listing created with type "+ dto.transaction_type());

        if (dto.price() != null) {
            Details details = new Details();
            details.setBookListing(listing);
            details.setPrice(dto.price());
            listing.setDetails(details);    //maybe this doesn't stay.

            if (dto.rentalDuration() != null ) { //     || dto.rentalStartDate() != null
                RentDetails rentDetails = new RentDetails();
                rentDetails.setDetails(details);
                rentDetails.setRentalDuration(dto.rentalDuration());
//                rentDetails.setRentalStartDate(dto.rentalStartDate());
                details.setRentDetails(rentDetails);
            }
        }
        return saved.getId();
    }

    @Transactional
    public Page<BookListingDTO> getAllListings(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<BookListing> listings = bookListingRepository.findAllWithDetails(pageable);
        return listings.map(this::toDTO);
    }

    public BookListingDTO toDTO(BookListing listing) {
        Details details = listing.getDetails();
        RentDetails rentDetails =  null;
        if(listing.getTransaction_type().equals("RENT"))
            rentDetails = details.getRentDetails();

        return new BookListingDTO(
                listing.getId(),
                listing.getBook_id(),
                bookDAO.findBookById(listing.getBook_id()),
                listing.getOwner_id(),
                userDAO.getUsernameById(listing.getOwner_id()),
                listing.getCondition(),
                listing.getTransaction_type(),
                listing.getStatus(),
                details != null ? details.getPrice() : null,
                rentDetails != null ? rentDetails.getRentalDuration() : null
//                rentDetails != null ? rentDetails.getRentalStartDate() : null
        );
    }
}

