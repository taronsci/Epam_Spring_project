package com.booky.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "bookrequest")
@Getter
@Setter
public class BookRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "requester_id", nullable = false)
    private Integer requesterId;

    @Column(name = "listing_id", nullable = false)
    private Integer listingId;

//    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private String status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

//    @OneToMany(mappedBy = "bookRequest", fetch = FetchType.LAZY)
//    private User user;

}
