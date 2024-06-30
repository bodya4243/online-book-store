package com.example.onlinebookstore.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.onlinebookstore.dto.BookDtoWithoutCategoryIds;
import com.example.onlinebookstore.dto.BookRequestDto;
import com.example.onlinebookstore.dto.BookResponseDto;
import com.example.onlinebookstore.exception.EntityNotFoundException;
import com.example.onlinebookstore.mapper.BookMapper;
import com.example.onlinebookstore.model.Book;
import com.example.onlinebookstore.model.Category;
import com.example.onlinebookstore.repository.BookRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    private BookRequestDto bookRequestDto;
    private Book book;
    private BookResponseDto bookResponseDto;

    @BeforeEach
    void setUp() {
        bookRequestDto = new BookRequestDto();
        bookRequestDto.setTitle("new title");
        bookRequestDto.setIsbn("1234567890120");
        bookRequestDto.setPrice(BigDecimal.valueOf(23.33));
        bookRequestDto.setAuthor("new author");
        bookRequestDto.setDescription("new description");
        Set<Long> categories = new HashSet<>();
        categories.add(1L);
        categories.add(2L);
        bookRequestDto.setCategoryIds(categories);
        bookRequestDto.setCoverImage("new coverImage");

        book = new Book();
        book.setId(1L);
        book.setTitle("new title");
        book.setIsbn("1234567890120");
        book.setPrice(BigDecimal.valueOf(23.33));
        book.setAuthor("new author");
        book.setDescription("new description");
        book.setCoverImage("new coverImage");

        Set<Category> bookCategories = new HashSet<>();
        bookCategories.add(new Category(1L)); // Assume Category has a constructor with id
        bookCategories.add(new Category(2L));
        book.setCategoryId(bookCategories);

        bookResponseDto = new BookResponseDto();
        bookResponseDto.setTitle("new title");
        bookResponseDto.setIsbn("1234567890120");
        bookResponseDto.setPrice(BigDecimal.valueOf(23.33));
        bookResponseDto.setAuthor("new author");
        bookResponseDto.setDescription("new description");
        Set<Long> categoriesForResponseDto = new HashSet<>();
        categoriesForResponseDto.add(1L);
        categoriesForResponseDto.add(2L);
        bookResponseDto.setCategoryIds(categoriesForResponseDto);
        bookResponseDto.setCoverImage("new coverImage");
    }

    @Test
    void testSave() {
        when(bookMapper.toModel(bookRequestDto)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(bookResponseDto);
        when(bookRepository.save(book)).thenReturn(book);

        BookResponseDto expected = bookResponseDto;
        BookResponseDto actual = bookService.save(bookRequestDto);

        assertEquals(expected, actual);
    }

    @Test
    void getAll() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Book> books = List.of(book);
        Page<Book> pagedBooks = new PageImpl<>(books, pageable, books.size());

        when(bookRepository.findAll(pageable)).thenReturn(pagedBooks);
        when(bookMapper.toDto(book)).thenReturn(bookResponseDto);

        List<BookResponseDto> expected = List.of(bookResponseDto);
        List<BookResponseDto> actual = bookService.getAll(pageable);

        assertEquals(expected, actual);
    }

    @Test
    void getById() {
        Long bookId = 1L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(bookResponseDto);

        BookResponseDto expected = bookResponseDto;
        BookResponseDto actual = bookService.getById(bookId);

        assertEquals(expected, actual);
    }

    @Test
    void getById_NonExistedId() {
        Long nonExistentId = 999L;
        when(bookRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> bookService.getById(nonExistentId));
    }

    @Test
    void deleteById() {
        Long bookId = 1L;

        when(bookRepository.existsById(bookId)).thenReturn(true);
        doNothing().when(bookRepository).deleteById(bookId);

        bookService.deleteById(bookId);

        verify(bookRepository, times(1)).existsById(bookId);
        verify(bookRepository, times(1)).deleteById(bookId);
    }

    @Test
    void updateById() {
        // Given
        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);
        BookResponseDto expectedDto = new BookResponseDto();
        expectedDto.setId(bookId);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(expectedDto);

        // When
        BookResponseDto actualDto = bookService.getById(bookId);

        // Then
        assertEquals(expectedDto, actualDto);
    }

    @Test
    void getBooksByCategoryId() {
        // Given
        Long categoryId = 1L;

        List<Book> books = new ArrayList<>();
        books.add(new Book());
        books.add(new Book());

        // When
        when(bookRepository.findAllByCategoryId(categoryId)).thenReturn(books);

        List<BookDtoWithoutCategoryIds> expectedDtos = books.stream()
                .map(bookMapper::toDtoWithoutCategories)
                .toList();

        List<BookDtoWithoutCategoryIds> foundDtos = bookService.getBooksByCategoryId(categoryId);

        // Then
        assertEquals(expectedDtos.size(), foundDtos.size());
        for (int i = 0; i < expectedDtos.size(); i++) {
            assertEquals(expectedDtos.get(i), foundDtos.get(i));
        }
    }
}
