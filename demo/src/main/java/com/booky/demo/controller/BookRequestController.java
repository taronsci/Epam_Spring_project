package com.booky.demo.controller;

import com.booky.demo.dto.BookRequestDTO;
import com.booky.demo.service.BookRequestService;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/request")
public class BookRequestController {
    private final BookRequestService bookRequestService;
    private final PagedResourcesAssembler<BookRequestDTO> assembler;

    public BookRequestController(BookRequestService bookRequestService, PagedResourcesAssembler<BookRequestDTO> assembler) {
        this.bookRequestService = bookRequestService;
        this.assembler = assembler;
    }

    @PostMapping
    public ResponseEntity<Integer> createRequest(@RequestBody BookRequestDTO requestDTO) {
        Integer listingId = bookRequestService.createRequest(requestDTO);
        return ResponseEntity.ok(listingId);
    }

    @GetMapping("/{ownerId}")
    public PagedModel<EntityModel<BookRequestDTO>> getBooksRequestsById(@PathVariable int ownerId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {
        System.out.println("this is good");

        Page<BookRequestDTO> requestPage = bookRequestService.getBookRequestsById(ownerId, page, size);
        return assembler.toModel(requestPage);
    }
}
