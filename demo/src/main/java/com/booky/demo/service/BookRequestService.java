package com.booky.demo.service;

import com.booky.demo.dao.BookRequestDAO;
import com.booky.demo.dao.BookRequestRepository;
import com.booky.demo.dto.BookRequestDTO;
import com.booky.demo.model.BookRequest;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookRequestService {
    private BookRequestRepository bookRequestRepository;
    private BookRequestDAO bookRequestDAO;

    public BookRequestService(BookRequestRepository bookRequestRepository, BookRequestDAO bookRequestDAO){
        this.bookRequestRepository = bookRequestRepository;
        this.bookRequestDAO = bookRequestDAO;
    }

    @Transactional
    public Integer createRequest(BookRequestDTO dto) {
        System.out.println("creating request");
        BookRequest request = new BookRequest();

        request.setRequesterId(dto.requesterId());
        request.setListingId(dto.listingId());
        request.setStatus("PENDING");
        request.setCreatedAt(dto.createdAt());

        request = bookRequestRepository.save(request);
        System.out.println("request created with id "+ request.getId());

        return request.getId();
    }

    @Transactional
    public Page<BookRequestDTO> getBookRequestsById(int ownerId, int page, int size){
        Pageable pageable = PageRequest.of(page,size);

        Page<BookRequestDTO> listings = bookRequestDAO.findRequests(ownerId, pageable);
        return listings;
    }

//    private BookRequestDTO toDTO(BookRequest request) {
//
//        return new BookListingDTO(
//                request.getId(),
//                request.getRequesterId(),
//                request.getListingId(),
//                request.getStatus(),
//
//                //I need Book of listing
//                request.get
//                userDAO.getUsernameById(listing.getOwner_id()),
//                listing.getCondition(),
//                listing.getTransaction_type(),
//                listing.getStatus(),
//                details != null ? details.getPrice() : null,
//                rentDetails != null ? rentDetails.getRentalDuration() : null
////                rentDetails != null ? rentDetails.getRentalStartDate() : null
//        );
//    }
}
