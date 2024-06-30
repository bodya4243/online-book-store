package com.example.onlinebookstore.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.example.onlinebookstore.model.Book;
import com.example.onlinebookstore.model.Category;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {
    @Container
    private static MySQLContainer mysql = new MySQLContainer()
            .withUsername("user")
            .withPassword("password")
            .withDatabaseName("testdb");

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Category category;

    @BeforeEach
    public void setUp() {
        category = new Category();
        category.setName("Fiction");
        category.setDescription("fiction books");

        category = categoryRepository.save(category);

        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setCategoryId(new HashSet<>());
        book.getCategoryId().add(category);
        book.setIsbn("9781122394457");
        book.setPrice(BigDecimal.valueOf(23.33));

        bookRepository.save(book);
    }

    @Test
    @DisplayName("""
            Find all books which belong to a category ID, returns list of books
            """)
    void findAllByCategoryId_ExistCategoryId_ReturnAllBooksByCategoryId() {
        // Given
        Long categoryId = category.getId();

        // When
        List<Book> foundBooks = bookRepository.findAllByCategoryId(categoryId);

        // Then
        assertEquals(1, foundBooks.size());
    }
}
