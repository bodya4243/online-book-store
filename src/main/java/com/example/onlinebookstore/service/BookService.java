package com.example.onlinebookstore.service;

import com.example.onlinebookstore.dto.BookDto;
import com.example.onlinebookstore.dto.BookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(BookRequestDto bookDto);
    
    List<BookDto> getAll(Pageable pageable);
    
    BookDto getById(Long id);
    
    void deleteById(Long id);

    BookDto updateById(Long id, BookRequestDto bookRequestDto);
}
