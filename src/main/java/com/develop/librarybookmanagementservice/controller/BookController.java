package com.develop.librarybookmanagementservice.controller;

import com.develop.librarybookmanagementservice.dtos.BookRequestDto;
import com.develop.librarybookmanagementservice.dtos.BookResponseDto;
import com.develop.librarybookmanagementservice.dtos.Response;
import com.develop.librarybookmanagementservice.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@Slf4j
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<BookResponseDto>> getBooks() {
        log.info("invoking getBooks method");
        return ResponseEntity.ok(bookService.getBooks());
    }

    @GetMapping("/{bookId}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<BookResponseDto> getBookById(@PathVariable("bookId") int bookId) {
        log.info("invoking getBookById method");
        return ResponseEntity.ok(bookService.getBookById(bookId));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<BookResponseDto>> getBookByTitle(@RequestParam String title) {
        log.info("invoking getBookByTitle method");
        log.info("searching for book with title: {}", title);
        return ResponseEntity.ok(bookService.getBookByTitle(title));
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Response> addBook(@RequestBody BookRequestDto bookRequestDto) {
        log.info("invoking addBook method");
        Response response = bookService.addBook(bookRequestDto);
        return ResponseEntity.ok(response);
    }

}
