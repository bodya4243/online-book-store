package com.example.onlinebookstore.dto.orderitem;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class OrderItemRequestDto {
    @NotNull
    private Long bookId;

    @Min(1)
    private int quantity;

    @NotNull
    @Min(value = 0, message = "can't be less than 0")
    private BigDecimal price;
}
