package com.booky.demo.service;

import com.booky.demo.dao.BookDAO;
import com.booky.demo.model.Book;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    private final BookDAO bookDAO;

    public BookService(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Transactional
    public Integer save(Book book) {
        Integer id = bookDAO.findByTitleAndAuthor(book.getTitle(), book.getAuthor());
        if (id == null)
            id = bookDAO.save(book);

        return id;
    }
}
