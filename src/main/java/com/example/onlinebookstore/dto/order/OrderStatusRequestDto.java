package com.example.onlinebookstore.dto.order;

import com.example.onlinebookstore.model.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderStatusRequestDto {
    private OrderStatus status;
}
