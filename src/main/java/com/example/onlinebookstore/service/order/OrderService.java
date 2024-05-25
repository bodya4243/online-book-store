package com.example.onlinebookstore.service.order;

import com.example.onlinebookstore.dto.order.OrderRequestDto;
import com.example.onlinebookstore.dto.order.OrderResponseDto;
import com.example.onlinebookstore.dto.orderitem.OrderItemResponseDto;
import com.example.onlinebookstore.model.User;
import java.util.List;

public interface OrderService {
    OrderResponseDto placeOrder(OrderRequestDto orderRequestDto, User user);

    List<OrderResponseDto> getOrderHistory(Long userId);

    List<OrderItemResponseDto> getOrderItems(Long orderId);

    OrderItemResponseDto getOrderItem(Long orderId, Long itemId);
}
