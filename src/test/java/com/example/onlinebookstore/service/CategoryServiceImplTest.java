package com.example.onlinebookstore.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.onlinebookstore.dto.CategoryRequestDto;
import com.example.onlinebookstore.dto.CategoryResponseDto;
import com.example.onlinebookstore.exception.EntityNotFoundException;
import com.example.onlinebookstore.mapper.CategoryMapper;
import com.example.onlinebookstore.model.Category;
import com.example.onlinebookstore.repository.CategoryRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    private CategoryRequestDto categoryRequestDto;
    private CategoryResponseDto categoryResponseDto;
    private List<Category> categories;
    private List<CategoryResponseDto> categoryDtos;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    void setUp() {
        Category category1 = new Category();
        Category category2 = new Category();
        categories = Arrays.asList(category1, category2);

        CategoryResponseDto dto1 = new CategoryResponseDto();
        CategoryResponseDto dto2 = new CategoryResponseDto();
        categoryDtos = Arrays.asList(dto1, dto2);
    }

    @Test
    void testGetAll() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Category> categoryPage = new PageImpl<>(categories);

        when(categoryRepository.findAll(pageable)).thenReturn(categoryPage);
        when(categoryMapper.toDto(categories.get(0))).thenReturn(categoryDtos.get(0));
        when(categoryMapper.toDto(categories.get(1))).thenReturn(categoryDtos.get(1));

        List<CategoryResponseDto> result = categoryService.getAll(pageable);

        assertEquals(categoryDtos, result);
    }

    @Test
    void testGetById() {
        // Given
        Long categoryId = 1L;
        Category category = new Category();
        category.setId(categoryId);
        CategoryResponseDto expectedDto = new CategoryResponseDto();
        expectedDto.setId(categoryId);

        // When
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(categoryMapper.toDto(category)).thenReturn(expectedDto);

        CategoryResponseDto actualDto = categoryService.getById(categoryId);

        // Then
        assertEquals(expectedDto, actualDto);
    }

    @Test
    void testSave() {
        CategoryResponseDto result = categoryService.save(categoryRequestDto);
        assertEquals(categoryResponseDto, result);
    }

    @Test
    void testUpdateById() {
        Category category = new Category();
        Long categoryId = 1L;
        category.setId(categoryId);
        when(categoryRepository.existsById(categoryId)).thenReturn(true);
        when(categoryMapper.toModel(categoryRequestDto)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(categoryResponseDto);
        when(categoryRepository.save(category)).thenReturn(category);

        CategoryResponseDto result = categoryService.updateById(categoryId, categoryRequestDto);
        assertEquals(categoryResponseDto, result);

        verify(categoryRepository).existsById(categoryId);
        verify(categoryRepository).save(category);
    }

    @Test
    void testUpdateByIdThrowsEntityNotFoundException() {
        Long id = 1L;
        when(categoryRepository.existsById(id)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () ->
                categoryService.updateById(id, categoryRequestDto));
    }

    @Test
    void testDeleteById() {
        Long id = 1L;
        when(categoryRepository.existsById(id)).thenReturn(true);
        doNothing().when(categoryRepository).deleteById(id);

        categoryService.deleteById(id);

        verify(categoryRepository).existsById(id);
        verify(categoryRepository).deleteById(id);
    }

    @Test
    void testDeleteByIdThrowsEntityNotFoundException() {
        Long id = 1L;
        when(categoryRepository.existsById(id)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> categoryService.deleteById(id));
    }
}
