package com.develop.librarybookmanagementservice.service;

import com.develop.librarybookmanagementservice.dtos.BookRequestDto;
import com.develop.librarybookmanagementservice.dtos.BookResponseDto;
import com.develop.librarybookmanagementservice.dtos.Response;

import java.util.List;

public interface BookService {
    Response addBook(BookRequestDto bookRequestDto);

    List<BookResponseDto> getBooks();

    BookResponseDto getBookById(int bookId);

    List<BookResponseDto> getBookByTitle(String title);

    Response addAllBooks(List<BookRequestDto> bookRequestDtos);

    Response deleteBook(int bookId);
}
