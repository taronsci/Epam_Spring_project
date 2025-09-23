package com.booky.demo.controller;

import com.booky.demo.dto.BookRequestDTO;
import com.booky.demo.service.BookRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/request")
public class BookRequestController {
    private final BookRequestService bookRequestService;

    public BookRequestController(BookRequestService bookRequestService) {
        this.bookRequestService = bookRequestService;
    }

    @PostMapping
    public ResponseEntity<Integer> createRequest(@RequestBody BookRequestDTO requestDTO) {
        Integer listingId = bookRequestService.createRequest(requestDTO);
        return ResponseEntity.ok(listingId);
    }
}
