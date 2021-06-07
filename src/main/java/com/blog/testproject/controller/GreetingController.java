package com.blog.testproject.controller;

import com.blog.testproject.exceptions.NotFoundException;
import com.blog.testproject.service.Greeting;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    public List<Map<String, String>> books = new ArrayList<Map<String, String>>() {
        {
            add(new HashMap<>() {{
                put("id", "1");
                put("book", "Clean Code");
            }});
            add(new HashMap<>() {{
                put("id", "2");
                put("book", "Java in Concurrency");
            }});
            add(new HashMap<>() {{
                put("id", "3");
                put("book", "Java Persistance");
            }});
            add(new HashMap<>() {{
                put("id", "4");
                put("book", "Pro Spring 5");
            }});
        }
    };

    @GetMapping("/list")
    public List<Map<String, String>> getBooks() {
        return books;
    }

    @GetMapping("/books/{id}")
    public Map<String, String> getBookById(@PathVariable String id) {
        return books.stream()
                .filter(book -> book.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);

    }


}
