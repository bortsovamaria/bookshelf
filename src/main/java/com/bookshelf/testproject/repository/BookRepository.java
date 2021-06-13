package com.bookshelf.testproject.repository;

import com.bookshelf.testproject.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
