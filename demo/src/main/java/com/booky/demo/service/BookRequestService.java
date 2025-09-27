package com.booky.demo.service;

import com.booky.demo.dao.BookRequestDAO;
import com.booky.demo.dao.BookRequestRepository;
import com.booky.demo.dto.BookRequestDTO;
import com.booky.demo.model.BookRequest;
import com.booky.demo.model.RequestStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookRequestService {
    private BookRequestRepository bookRequestRepository;
    private BookRequestDAO bookRequestDAO;

    public BookRequestService( BookRequestRepository bookRequestRepository, BookRequestDAO bookRequestDAO){
        this.bookRequestRepository = bookRequestRepository;
        this.bookRequestDAO = bookRequestDAO;
    }

    @Transactional
    public Integer createRequest(BookRequestDTO dto) {
        BookRequest request = new BookRequest();
        request.setRequesterId(dto.requesterId());
        request.setListingId(dto.listingId());
        request.setStatus(RequestStatus.PENDING);
        request.setCreatedAt(dto.createdAt());

        request = bookRequestRepository.save(request);

        return request.getId();
    }

    @Transactional
    public Page<BookRequestDTO> getBookRequestsById(int ownerId, int page, int size){
        Pageable pageable = PageRequest.of(page,size);
        return bookRequestDAO.findRequests(ownerId, pageable);
    }

    @Transactional
    public Page<BookRequestDTO> getMyRequests(int ownerId, int page, int size){
        Pageable pageable = PageRequest.of(page,size);
        return bookRequestDAO.findMyRequests(ownerId, pageable);
    }

    public boolean deleteRequest(int id){
        return bookRequestDAO.deleteById(id) > 0;
    }

    @Transactional
    public boolean updateStatus(int id, RequestStatus status){
        return bookRequestDAO.updateStatus(id, status) > 0;
    }
}
