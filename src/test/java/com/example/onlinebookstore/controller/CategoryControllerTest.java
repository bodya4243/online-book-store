package com.example.onlinebookstore.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.onlinebookstore.dto.BookDtoWithoutCategoryIds;
import com.example.onlinebookstore.dto.CategoryRequestDto;
import com.example.onlinebookstore.dto.CategoryResponseDto;
import com.example.onlinebookstore.service.BookService;
import com.example.onlinebookstore.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryControllerTest {

    @Autowired
    private static MockMvc mockMvc;

    @Mock
    private CategoryService categoryService;

    @Mock
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    private CategoryRequestDto categoryRequestDto;
    private CategoryResponseDto categoryResponseDto;
    private List<CategoryResponseDto> categoryResponseDtoList;
    private List<BookDtoWithoutCategoryIds> bookDtoWithoutCategoryIdsList;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @BeforeEach
    void setUp() {
        categoryRequestDto = new CategoryRequestDto();
        categoryResponseDto = new CategoryResponseDto();
        categoryResponseDtoList = Arrays.asList(categoryResponseDto);
        bookDtoWithoutCategoryIdsList = Arrays.asList(new BookDtoWithoutCategoryIds());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createCategory() throws Exception {
        // Prepare the request DTO
        CategoryRequestDto categoryRequestDto = new CategoryRequestDto();
        categoryRequestDto.setName("Fiction");
        categoryRequestDto.setDescription("Fiction books");

        // Perform the request and validate the response
        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteCategory() throws Exception {
        mockMvc.perform(delete("/categories/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
