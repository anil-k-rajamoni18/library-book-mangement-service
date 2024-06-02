package com.develop.librarybookmanagementservice.repository;

import com.develop.librarybookmanagementservice.entity.BookRowAssociation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRowAssociationRepo extends JpaRepository<BookRowAssociation, Integer> {
    Optional<BookRowAssociation> findByBookId(int bookId);
}
