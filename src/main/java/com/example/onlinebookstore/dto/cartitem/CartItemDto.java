package com.example.onlinebookstore.dto.cartitem;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartItemDto {
    @NotNull
    private Long bookId;

    @NotNull
    @Min(1)
    private int quantity;
}
