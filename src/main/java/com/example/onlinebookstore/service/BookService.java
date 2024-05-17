package com.example.onlinebookstore.service;

import com.example.onlinebookstore.dto.BookDtoWithoutCategoryIds;
import com.example.onlinebookstore.dto.BookRequestDto;
import com.example.onlinebookstore.dto.BookResponseDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookResponseDto save(BookRequestDto bookDto);
    
    List<BookResponseDto> getAll(Pageable pageable);

    BookResponseDto getById(Long id);
    
    void deleteById(Long id);

    BookResponseDto updateById(Long id, BookRequestDto bookRequestDto);

    List<BookDtoWithoutCategoryIds> getBooksByCategoryId(Long id);
}
