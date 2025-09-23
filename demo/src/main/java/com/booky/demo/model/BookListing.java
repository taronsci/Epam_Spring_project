package com.booky.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "booklisting")
@Getter
@Setter
public class BookListing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "book_id")
    private Integer book_id;

    @Column(name = "owner_id")
    private Integer owner_id;

//    @Enumerated(EnumType.STRING)
    @Column(name = "condition", nullable = false)
    private String condition;

//    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private String transaction_type;

//    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private String status;

    @OneToOne(mappedBy = "bookListing", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
    private Details details;


//    @OneToMany
//    @JoinColumn(name = "book_id", referencedColumnName = "id")
//    @JsonBackReference
//    // or @JsonIgnore
//    private Book book;
//
//    @OneToMany
//    @JoinColumn(name = "owner_id", referencedColumnName = "id")
//    @JsonBackReference
//    // or @JsonIgnore
//    private User user;
}
