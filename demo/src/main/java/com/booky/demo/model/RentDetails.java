package com.booky.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "rentdetails")
@Getter
@Setter
public class RentDetails {
    @Id
    @Column(name = "listing_id")
    private Integer id; // same as Details.id

    @MapsId
    @OneToOne
    @JoinColumn(name = "listing_id")
    private Details details;

    private LocalDate rentalStartDate; // nullable until rented
    private Integer rentalDuration;
}
