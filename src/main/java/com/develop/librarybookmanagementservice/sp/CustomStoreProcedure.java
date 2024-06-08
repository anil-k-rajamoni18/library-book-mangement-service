package com.develop.librarybookmanagementservice.sp;

import com.develop.librarybookmanagementservice.exception.BookException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;

@Slf4j
@Component
public class CustomStoreProcedure {
    public Map<String, Integer> addBook(Connection conn, String title, String author, String publisher, String publicationDate, String language, String genre, int[] rowId) {
        // Find a row with less than 10 books
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT row_id FROM BOOK_ROW_ASSOCIATIONS GROUP BY row_id HAVING COUNT(book_id) < 10 LIMIT 1")
        ) {

            if (rs.next()) {
                rowId[0] = rs.getInt("row_id");
            } else {
                // Create a new row if no such row exists
                try (PreparedStatement pstmt = conn.prepareStatement("INSERT INTO BOOK_ROWS () VALUES ()", Statement.RETURN_GENERATED_KEYS)) {
                    pstmt.executeUpdate();
                    try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            rowId[0] = generatedKeys.getInt(1);
                        }
                    }
                }
            }

            // Insert the book
            int bookId;
            try (PreparedStatement pstmt = conn.prepareStatement("INSERT INTO BOOKS (TITLE, AUTHOR, PUBLISHER,PUBLICATION_DATE, LANGUAGE, GENRE) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, title);
                pstmt.setString(2, author);
                pstmt.setString(3, publisher);
                pstmt.setString(4, publicationDate);
                pstmt.setString(5, language);
                pstmt.setString(6, genre);

                pstmt.executeUpdate();
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        bookId = generatedKeys.getInt(1);
                    } else {
                        throw new Exception("Failed to insert book");
                    }
                }
            }

            // Associate the book with the row
            try (PreparedStatement pstmt = conn.prepareStatement("INSERT INTO BOOK_ROW_ASSOCIATIONS (row_id, book_id) VALUES (?, ?)")) {
                pstmt.setInt(1, rowId[0]);
                pstmt.setInt(2, bookId);
                pstmt.executeUpdate();
            }
            log.info("Book added successfully with id: {} in rowNumber: {}", bookId, rowId[0]);
            return Map.of("bookId", bookId, "rowId", rowId[0]);
        } catch (Exception ex) {
            log.error("Failed to add book", ex);
            throw new BookException("500", ex.getMessage());
        }
    }
}
