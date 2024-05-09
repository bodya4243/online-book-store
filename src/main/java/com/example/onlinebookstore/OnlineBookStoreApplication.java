package com.example.onlinebookstore;

import com.example.onlinebookstore.service.BookService;
import com.example.onlinebookstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@RequiredArgsConstructor
@SpringBootApplication
public class OnlineBookStoreApplication {
    private BookService bookService;

    private UserService userService;
    
    public static void main(String[] args) {
        SpringApplication.run(OnlineBookStoreApplication.class, args);
    }
}
