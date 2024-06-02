package com.develop.librarybookmanagementservice.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class BookRequestDto {
    private String title;
    private String author;
    private String publisher;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Timestamp publicationDate;
    private String language;
    private String genre;
}
