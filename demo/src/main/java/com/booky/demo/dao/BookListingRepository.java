package com.booky.demo.dao;

import com.booky.demo.model.BookListing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookListingRepository extends JpaRepository<BookListing,Integer> {

    @Query("SELECT b FROM BookListing b LEFT JOIN FETCH b.details d LEFT JOIN FETCH d.rentDetails")
    Page<BookListing> findAllWithDetails(Pageable pageable);
}
