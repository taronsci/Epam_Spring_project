package com.booky.demo.service;

import com.booky.demo.dao.BookDAO;
import com.booky.demo.model.Book;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {
    private final BookDAO bookDAO;

    public BookService(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Transactional
    public Integer save(Book book) {
        Optional<Integer> id = bookDAO.findByTitleAndAuthor(book.getTitle(), book.getAuthor());
        return id.orElseGet(() -> bookDAO.save(book));
    }
}
