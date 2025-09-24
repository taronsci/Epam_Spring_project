package com.booky.demo.dao;

import com.booky.demo.dto.BookRequestDTO;
import com.booky.demo.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookRequestDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookRequestDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Page<BookRequestDTO> findRequests(int ownerId, Pageable pageable){
        Integer total = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM bookrequest br " +
                        "LEFT JOIN booklisting bl ON bl.id = br.listing_id " +
                        "WHERE bl.owner_id = ?",
                Integer.class,
                ownerId
        );

        String sql = """
            SELECT u.username AS requester,
                   br.id AS request_id,
                   br.requester_id,
                   br.listing_id,
                   bl.owner_id AS owner,
                   b.title,
                   b.id AS book_id,
                   b.title,
                   b.author,
                   b.year,
                   b.genre,
                   br.status,
                   br.created_at
            FROM bookrequest br
            LEFT JOIN booklisting bl ON bl.id = br.listing_id
            LEFT JOIN users u ON u.id = br.requester_id
            LEFT JOIN book b ON b.id = bl.book_id
            WHERE bl.owner_id = ?
            ORDER BY br.created_at asc
            LIMIT ? OFFSET ?
        """;
        List<BookRequestDTO> content = jdbcTemplate.query(sql,
                (rs, rowNum) -> {
                    Book book = new Book();
                    book.setId(rs.getInt("book_id"));
                    book.setTitle(rs.getString("title"));
                    book.setAuthor(rs.getString("author"));
                    book.setYear(rs.getInt("year"));
                    book.setGenre(rs.getString("genre"));

                    return new BookRequestDTO(
                            rs.getInt("request_id"),
                            rs.getString("requester"),
                            rs.getInt("requester_id"),
                            rs.getInt("listing_id"),
                            rs.getString("status"),
                            book,
                            rs.getInt("owner"),
                            rs.getTimestamp("created_at").toLocalDateTime(),
                            null
                    );
                },
                ownerId,
                pageable.getPageSize(),
                pageable.getOffset()
        );
        return new PageImpl<>(content,pageable, total);

    }
//    public List<BookRequestDTO> findRequestsForOwner(int ownerId, int page, int size) {
//        int offset = page * size;
//        String sql = """
//            SELECT u.username AS requester,
//                   br.requester_id,
//                   br.listing_id,
//                   bl.owner_id AS owner,
//                   b.title,
//                   b.id AS book_id,
//                   b.title,
//                   b.author,
//                   b.year,
//                   b.genre,
//                   br.status,
//                   br.created_at
//            FROM bookrequest br
//            LEFT JOIN booklisting bl ON bl.id = br.listing_id
//            LEFT JOIN users u ON u.id = br.requester_id
//            LEFT JOIN book b ON b.id = bl.book_id
//            WHERE bl.owner_id = ?
//            LIMIT ? OFFSET ?
//        """;
//
//        return jdbcTemplate.query(sql,
//                (rs, rowNum) -> {
//                    Book book = new Book();
//                    book.setId(rs.getInt("book_id"));
//                    book.setTitle(rs.getString("title"));
//                    book.setAuthor(rs.getString("author"));
//                    book.setYear(rs.getInt("year"));
//                    book.setGenre(rs.getString("genre"));
//
//                    return new BookRequestDTO(
//                            rs.getInt("requester"),
//                            rs.getInt("requester_id"),
//                            rs.getInt("listing_id"),
//                            rs.getString("status"),
//                            book,
//                            rs.getInt("owner"),
//                            rs.getTimestamp("created_at").toLocalDateTime(),
//                            null
//                    );
//                },
//                ownerId, size, offset
//        );
//    }

}
