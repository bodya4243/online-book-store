package com.example.onlinebookstore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class BookRequestDto {
    @NotNull
    private String title;

    @NotNull
    private String author;

    @NotNull
    @Min(value = 0, message = "can't be less than 0")
    private BigDecimal price;

    @NotNull
    private String isbn;

    private String description;

    private String coverImage;
}
