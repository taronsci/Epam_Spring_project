package com.booky.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "bookrequest")
@Getter
@Setter
public class BookRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // ---- Relationships ----
    @Column(name = "requester_id", nullable = false)
    private Integer requesterId;

    @Column(name = "listing_id", nullable = false)
    private Integer listingId;


//    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private String status;


    // ---- Constructors ----
    public BookRequest() {}

    public BookRequest(Integer requesterId, Integer listingId, String status) {
        this.requesterId = requesterId;
        this.listingId = listingId;
        this.status = status;
    }

}
