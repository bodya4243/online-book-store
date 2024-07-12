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
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/clean-up.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = "/set-up.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public class BookControllerIntegrationTest {

    @Container
    private static MySQLContainer mysql = new MySQLContainer()
            .withUsername("user")
            .withPassword("password")
            .withDatabaseName("testdb");

    @Autowired
    private static MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    private BookRequestDto bookRequestDto;

    private BookResponseDto bookResponseDto;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext context) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
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
    public void getAllBooks_ReturnsPageOfBooks_WhenExist() throws Exception {
        // When
        String result = mockMvc.perform(get("/books").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Then
        List<BookResponseDto> actual = objectMapper.readValue(result, new TypeReference<>() {});

        // Verify
        assertNotNull(actual);
        assertEquals(3, actual.size());
    }

    @WithMockUser(roles = {"ADMIN"})
    @Test
    public void createBook_CreatesNewBook_WhenRequestIsValid() throws Exception {
        // Given
        String jsonRequest = objectMapper.writeValueAsString(bookRequestDto);

        // When
        String result = mockMvc.perform(post("/books")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        // Then
        BookResponseDto actual = objectMapper.readValue(result, BookResponseDto.class);

        // Verify
        assertNotNull(actual);
        assertTrue(EqualsBuilder.reflectionEquals(bookResponseDto, actual, "id"));
    }
}
