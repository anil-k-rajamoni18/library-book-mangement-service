package com.develop.librarybookmanagementservice.repository;

import com.develop.librarybookmanagementservice.dtos.BookRequestDto;
import com.develop.librarybookmanagementservice.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query("SELECT * FROM BOOK WHERE TITLE LIKE %:title%")
    Optional<List<Book>> getBookByTitle(@Param("title") String title);
    //int addBookToDb(BookRequestDto bookRequestDto);
}
