package com.example.onlinebookstore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Set;
import lombok.Data;
import org.hibernate.validator.constraints.ISBN;

@Data
public class BookRequestDto {
    @NotNull
    @Size(min = 2)
    private String title;

    @NotNull
    @Size(min = 2)
    private String author;

    @NotNull
    @Min(value = 0, message = "can't be less than 0")
    private BigDecimal price;

    @ISBN
    private String isbn;

    private String description;

    private String coverImage;

    private Set<Long> categoryIds;
}
