package com.booky.demo.dao;

import com.booky.demo.model.BookRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRequestRepository extends JpaRepository<BookRequest,Integer> {


//    Page<BookRequest> findByIdWithDetails(@Param("ownerId") int ownerId, Pageable pageable);
}
