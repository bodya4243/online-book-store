package com.example.onlinebookstore.service;

import com.example.onlinebookstore.dto.BookDto;
import com.example.onlinebookstore.dto.BookRequestDto;
import java.util.List;

public interface BookService {
    BookDto save(BookRequestDto bookDto);
    
    List<BookDto> findAll();
    
    BookDto findById(Long id);
    
    void deleteById(Long id);

    BookDto updateById(Long id, BookRequestDto bookRequestDto);

}
