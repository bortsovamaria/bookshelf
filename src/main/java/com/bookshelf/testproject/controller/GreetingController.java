package com.bookshelf.testproject.controller;

import com.bookshelf.testproject.exceptions.NotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("books")
public class GreetingController {

    private int counter = 5;

    public List<Map<String, String>> books = new ArrayList<Map<String, String>>() {{
            add(new HashMap<>() {{ put("id", "1"); put("book", "Clean Code"); }});
            add(new HashMap<>() {{ put("id", "2"); put("book", "Java in Concurrency"); }});
            add(new HashMap<>() {{ put("id", "3"); put("book", "Java Persistance"); }});
            add(new HashMap<>() {{ put("id", "4"); put("book", "Pro Spring 5"); }});
        }};

    @GetMapping("")
    public List<Map<String, String>> getBooks() {
        return books;
    }

    @GetMapping("{id}")
    public Map<String, String> getBookById(@PathVariable String id) {
        return getBook(id);
    }

    private Map<String, String> getBook(@PathVariable String id) {
        return books.stream()
                .filter(book -> book.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public Map<String, String> create(@RequestBody Map<String, String> book) {
        book.put("id", String.valueOf(counter++));
        books.add(book);
        return book;
    }

    @PutMapping("{id}")
    public Map<String, String> update(@PathVariable String id, @RequestBody Map<String, String> book) {
        Map<String, String> bookFromDB = getBook(id);

        bookFromDB.putAll(book);
        bookFromDB.put("id", id);

        return bookFromDB;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        Map<String, String> book = getBook(id);

        books.remove(book);
    }
}
