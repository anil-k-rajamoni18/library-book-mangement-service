package com.develop.librarybookmanagementservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "BookRowAssociations")
@IdClass(BookRowAssociationId.class)
@Data
public class BookRowAssociation {
    @Id
    @ManyToOne
    @JoinColumn(name = "row_id")
    private BookRow bookRow;

    @Id
    @ManyToOne
    @JoinColumn(name = "bookId")
    private Book book;

}
