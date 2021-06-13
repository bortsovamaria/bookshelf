package com.bookshelf.testproject.controller;

import com.bookshelf.testproject.domain.Book;
import com.bookshelf.testproject.domain.Views;
import com.bookshelf.testproject.repository.BookRepository;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("books")
public class BookController {

    private final BookRepository bookRepository;

    @Autowired
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping
    @JsonView(Views.IdName.class)
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("{id}")
    @JsonView(Views.DateId.class)
    public Book getBookById(@PathVariable("id") Book book) {
        return book;
    }

    @PostMapping
    public Book create(@RequestBody Book book) {
        book.setCreationDate(LocalDateTime.now());
        return bookRepository.save(book);
    }

    @PutMapping("{id}")
    public Book update(
            @PathVariable("id") Book bookFromDb,
            @RequestBody Book book
    ) {
        BeanUtils.copyProperties(book, bookFromDb, "id");

        return bookRepository.save(bookFromDb);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Book book) {
        bookRepository.delete(book);
    }
}
