package com.example.onlinebookstore.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.onlinebookstore.dto.BookRequestDto;
import com.example.onlinebookstore.dto.BookResponseDto;
import com.example.onlinebookstore.service.BookService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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
public class BookControllerTest {

    @Autowired
    private static MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private BookController bookController;

    private BookRequestDto bookRequestDto;

    private BookResponseDto bookResponseDto;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @BeforeEach
    void setUp() {
        bookRequestDto = new BookRequestDto();
        bookRequestDto.setTitle("Sample Book 3");
        bookRequestDto.setIsbn("9781122334457");
        bookRequestDto.setPrice(BigDecimal.valueOf(29.99));
        bookRequestDto.setAuthor("Author C");
        bookRequestDto.setDescription("Yet another sample book description.");
        bookRequestDto.setCategoryIds(Set.of(1L, 2L));
        bookRequestDto.setCoverImage("http://example.com/cover3.jpg");

        bookResponseDto = new BookResponseDto();
        bookResponseDto.setId(1L);
        bookResponseDto.setTitle(bookRequestDto.getTitle());
        bookResponseDto.setAuthor(bookRequestDto.getAuthor());
        bookResponseDto.setPrice(bookRequestDto.getPrice());
        bookResponseDto.setIsbn(bookRequestDto.getIsbn());
        bookResponseDto.setDescription(bookRequestDto.getDescription());
        bookResponseDto.setCoverImage(bookRequestDto.getCoverImage());
        bookResponseDto.setCategoryIds(bookRequestDto.getCategoryIds());
    }

    @WithMockUser(username = "user", roles = {"ADMIN"})
    @Test
    public void testGetAllBooks() throws Exception {
        // When
        String result = mockMvc.perform(get("/books").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Then
        List<BookResponseDto> actual = objectMapper.readValue(result,
                new TypeReference<>() {
                });

        // Verify
        assertNotNull(actual);
        assertEquals(3, actual.size());
    }

    @WithMockUser(roles = {"ADMIN"})
    @Test
    public void testCreateBook() throws Exception {
        // Given
        String jsonRequest = objectMapper.writeValueAsString(bookRequestDto);

        // When
        String result = mockMvc.perform(post("/books")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        // Then
        BookResponseDto actual = objectMapper.readValue(result, BookResponseDto.class);

        // Verify
        assertNotNull(actual);
        assertTrue(EqualsBuilder.reflectionEquals(bookResponseDto, actual, "id"));
    }
}
