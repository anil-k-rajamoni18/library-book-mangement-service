package com.develop.librarybookmanagementservice.service.impl;

import com.develop.librarybookmanagementservice.dtos.BookRequestDto;
import com.develop.librarybookmanagementservice.dtos.BookResponseDto;
import com.develop.librarybookmanagementservice.dtos.Response;
import com.develop.librarybookmanagementservice.entity.Book;
import com.develop.librarybookmanagementservice.entity.BookRowAssociation;
import com.develop.librarybookmanagementservice.exception.BookException;
import com.develop.librarybookmanagementservice.repository.BookRepository;
import com.develop.librarybookmanagementservice.repository.BookRowAssociationRepo;
import com.develop.librarybookmanagementservice.service.BookService;
import com.develop.librarybookmanagementservice.sp.CustomStoreProcedure;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class BookServiceImpl  implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookRowAssociationRepo bookRowAssociationRepo;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private CustomStoreProcedure storeProcedure;


    @Override
    public Response addBook(BookRequestDto bookRequestDto) {

        Map<String, Integer> resultMap = new HashMap<>();
        try {
            Connection conn = dataSource.getConnection();
            resultMap = storeProcedure.addBook(conn,
                    bookRequestDto.getTitle(),
                    bookRequestDto.getAuthor(),
                    bookRequestDto.getPublisher(),
                    bookRequestDto.getPublicationDate().toString(),
                    bookRequestDto.getLanguage(),
                    bookRequestDto.getGenre(),
                    new int[10]
            );
            conn.close();
            if (resultMap.getOrDefault("rowId", 0) != 0) {
                return Response.builder()
                        .message(String.format("Book added with id: %s successfully in rowNumber: %s", resultMap.get("bookId"), resultMap.get("rowId")))
                        .status("Success")
                        .build();
            } else {
                return Response.builder().message("Failed to add book").status("Failed").build();
            }

        } catch (SQLException e) {
            throw new BookException("SQL Connection failed", "500");
        }
    }

    @Override
    public List<BookResponseDto> getBooks() {
        List<Book> dbBookList= bookRepository.findAll();
        return dbBookList.stream()
                .map(book -> BookResponseDto.builder()
                        .title(book.getTitle())
                        .id(book.getId())
                        .rowNumber(getBookRowNumber(book.getId()))
                        .author(book.getAuthor())
                        .publisher(book.getPublisher())
                        .publicationDate(book.getPublicationDate())
                        .language(book.getLanguage())
                        .genre(book.getGenre())
                        .build())
                .toList();
    }

    @Override
    public BookResponseDto getBookById(int bookId) {
        return bookRepository.findById(bookId)
                .map(book -> BookResponseDto.builder()
                        .title(book.getTitle())
                        .id(book.getId())
                        .rowNumber(getBookRowNumber(book.getId()))
                        .author(book.getAuthor())
                        .publisher(book.getPublisher())
                        .publicationDate(book.getPublicationDate())
                        .language(book.getLanguage())
                        .genre(book.getGenre())
                        .build())
                .orElseThrow(() -> new BookException(String.format("Book not found with id: %s", bookId), "404"));
    }

    @Override
    public List<BookResponseDto> getBookByTitle(String title) {
        List<BookResponseDto> books = bookRepository.getBookByTitle(title)
                .orElseThrow(() -> new BookException(String.format("Book not found with title: %s", title), "404"))
                .stream()
                .map(book -> BookResponseDto.builder()
                        .title(book.getTitle())
                        .id(book.getId())
                        .rowNumber(getBookRowNumber(book.getId()))
                        .author(book.getAuthor())
                        .publisher(book.getPublisher())
                        .publicationDate(book.getPublicationDate())
                        .language(book.getLanguage())
                        .genre(book.getGenre())
                        .build())
                .toList();
        return books;
    }

    @Override
    public Response addAllBooks(List<BookRequestDto> bookRequestDtos) {
        try {
            bookRequestDtos.forEach(this::addBook);
            return Response.builder().message("All books added successfully").status("Success").build();
        } catch (Exception e) {
            log.error("Exception occurred while adding all books | ErrorMessage: {}", e.getMessage());
            throw new BookException("Failed to add all books", "500");
        }
    }

    @Override
    public Response deleteBook(int bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        book.ifPresentOrElse(bookRepository::delete, () -> {
            throw new BookException(String.format("Book not found with id: %s", bookId), "404");
        });
        return Response.builder().message(String.format("Book with id: %s deleted successfully", bookId)).status("Success").build();
    }

    private int getBookRowNumber(int bookId) {
        BookRowAssociation bookRowAssociation = bookRowAssociationRepo.findByBookId(bookId)
                .orElseThrow(() -> new BookException(String.format("Book not found with id: %s", bookId), "404"));
        return bookRowAssociation.getBookRow().getId();
    }
}
