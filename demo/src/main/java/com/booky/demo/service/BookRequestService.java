package com.booky.demo.service;

import com.booky.demo.dao.BookRequestRepository;
import com.booky.demo.dto.BookRequestDTO;
import com.booky.demo.model.BookRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class BookRequestService {
    private BookRequestRepository bookRequestRepository;

    public BookRequestService(BookRequestRepository bookRequestRepository){
        this.bookRequestRepository = bookRequestRepository;
    }

    @Transactional
    public Integer createRequest(BookRequestDTO dto) {
        System.out.println("creating request");
        BookRequest request = new BookRequest();

        request.setRequesterId(dto.requesterId());
        request.setListingId(dto.listingId());
        request.setStatus("PENDING");

        request = bookRequestRepository.save(request);
        System.out.println("request created with id "+ request.getId());

        return request.getId();
    }
}
