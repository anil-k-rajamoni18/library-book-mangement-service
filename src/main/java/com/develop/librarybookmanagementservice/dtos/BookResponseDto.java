package com.develop.librarybookmanagementservice.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookResponseDto {
    private int id;
    private String title;
    private String author;
    private String publisher;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Timestamp publicationDate;
    private String language;
    private String genre;
    private int rowNumber;
}
