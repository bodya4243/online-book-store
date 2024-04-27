package com.example.onlinebookstore.service;

import com.example.onlinebookstore.dto.BookDto;
import com.example.onlinebookstore.dto.CreateBookRequestDto;
import java.util.List;

public interface BookService {
    BookDto save(CreateBookRequestDto bookDto);
    
    List<BookDto> findAll();
    
    BookDto findById(Long id);
    
    void deleteById(Long id);
}
