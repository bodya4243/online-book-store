package com.example.onlinebookstore.service;

import com.example.onlinebookstore.dto.BookDto;
import com.example.onlinebookstore.dto.BookRequestDto;
import com.example.onlinebookstore.exception.EntityNotFoundException;
import com.example.onlinebookstore.mapper.BookMapper;
import com.example.onlinebookstore.model.Book;
import com.example.onlinebookstore.repository.BookRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    
    private final BookMapper bookMapper;
    
    @Override
    public BookDto save(BookRequestDto bookDto) {
        Book book = bookMapper.toModel(bookDto);
        return bookMapper.toDto(bookRepository.save(book));
    }
    
    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream().map(bookMapper::toDto).toList();
    }
    
    @Override
    public BookDto findById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't get book by id" + id));
        return bookMapper.toDto(book);
    }
    
    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public BookDto updateById(Long id, BookRequestDto bookDto) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Can`t find a book with id: " + id);
        }
        Book book = bookMapper.toModel(bookDto);
        book.setId(id);
        return bookMapper.toDto(bookRepository.save(book));
    }
}
