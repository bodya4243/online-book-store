package com.example.onlinebookstore.controller;

import com.example.onlinebookstore.dto.order.OrderRequestDto;
import com.example.onlinebookstore.dto.order.OrderResponseDto;
import com.example.onlinebookstore.dto.order.OrderStatusRequestDto;
import com.example.onlinebookstore.dto.orderitem.OrderItemResponseDto;
import com.example.onlinebookstore.model.User;
import com.example.onlinebookstore.service.order.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order", description = "Endpoints for managing orders")
@RequiredArgsConstructor
@RestController
@ResponseStatus(HttpStatus.OK)
@RequestMapping(value = "/orders")
public class OrderController {
    private final OrderService orderService;

    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "create an order", description = "Create an Order")
    @PostMapping
    public OrderResponseDto placeOrder(@RequestBody @Valid OrderRequestDto orderRequestDto,
                                             @AuthenticationPrincipal User user) {
        return orderService.placeOrder(orderRequestDto, user);
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "get all books", description = "Get all books")
    @GetMapping
    public List<OrderResponseDto> getOrderHistory(
            @AuthenticationPrincipal User user) {
        return orderService.getOrderHistory(user.getId());
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "get order items", description = "Get order Items")
    @GetMapping("/{orderId}/items")
    public List<OrderItemResponseDto> getOrderItems(@PathVariable Long orderId) {
        return orderService.getOrderItems(orderId);
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "get an order item", description = "get an order item")
    @GetMapping("/{orderId}/items/{id}")
    public OrderItemResponseDto getOrderItem(@PathVariable Long orderId, @PathVariable Long id) {
        return orderService.getOrderItem(orderId, id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponseDto updateOrderStatus(@PathVariable Long orderId,
                                              @RequestBody @Valid OrderStatusRequestDto status,
                                              @AuthenticationPrincipal User user) {
        return orderService.updateOrderStatus(orderId, status, user);
    }
}
