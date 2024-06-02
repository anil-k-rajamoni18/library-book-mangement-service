package com.develop.librarybookmanagementservice.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class BookRowAssociationId implements Serializable {
    private int bookRow;
    private int book;
}
