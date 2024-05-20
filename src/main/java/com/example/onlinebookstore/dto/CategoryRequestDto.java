package com.example.onlinebookstore.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryRequestDto {
    @NotNull
    @Size(min = 2, max = 20)
    private String name;

    @Size(min = 5, max = 3000)
    private String description;
}
