package com.example.onlinebookstore.service.order;

import com.example.onlinebookstore.dto.order.OrderRequestDto;
import com.example.onlinebookstore.dto.order.OrderResponseDto;
import com.example.onlinebookstore.dto.order.OrderStatusRequestDto;
import com.example.onlinebookstore.dto.orderitem.OrderItemResponseDto;
import com.example.onlinebookstore.exception.EntityNotFoundException;
import com.example.onlinebookstore.exception.OrderItemBadRequestException;
import com.example.onlinebookstore.mapper.CartItemToOrderItemMapper;
import com.example.onlinebookstore.mapper.OrderItemMapper;
import com.example.onlinebookstore.mapper.OrderMapper;
import com.example.onlinebookstore.model.Order;
import com.example.onlinebookstore.model.OrderItem;
import com.example.onlinebookstore.model.OrderStatus;
import com.example.onlinebookstore.model.ShoppingCart;
import com.example.onlinebookstore.model.User;
import com.example.onlinebookstore.repository.OrderItemRepository;
import com.example.onlinebookstore.repository.OrderRepository;
import com.example.onlinebookstore.repository.ShoppingCartRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final CartItemToOrderItemMapper createOrderItems;

    @Override
    public OrderResponseDto placeOrder(OrderRequestDto orderRequestDto, User user) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Can't find shopping cart for user with id: %d",
                                user.getId())));

        Order order = buildOrder(user, orderRequestDto, shoppingCart, createOrderItems);

        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    public List<OrderResponseDto> getOrderHistory(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);

        return orders.stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public List<OrderItemResponseDto> getOrderItems(Long orderId) {
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);
        return orderItems.stream().map(orderItemMapper::toDto).toList();
    }

    @Override
    public OrderItemResponseDto getOrderItem(Long orderId, Long itemId) {
        OrderItem orderItem = orderItemRepository.findById(itemId)
                .orElseThrow(() -> new OrderItemBadRequestException("Order item not found"));
        if (!orderItem.getOrder().getId().equals(orderId)) {
            throw new OrderItemBadRequestException("Order item does not"
                    + "belong to the specified order");
        }
        return orderItemMapper.toDto(orderItem);
    }

    @Override
    public OrderResponseDto updateOrderStatus(Long orderId,
                                              OrderStatusRequestDto orderStatusRequestDto,
                                              User user) {
        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new EntityNotFoundException(
                        "Unable to proceed: Order not found with id: " + orderId));
        order.setStatus(orderStatusRequestDto.getStatus());
        orderRepository.save(order);
        return orderMapper.toDto(order);
    }

    private Order buildOrder(
            User user,
            OrderRequestDto orderRequestDto,
            ShoppingCart shoppingCart,
            CartItemToOrderItemMapper createOrderItems) {
        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setShippingAddress(orderRequestDto.getShippingAddress());
        BigDecimal total = calculateTotal(shoppingCart);
        order.setTotal(total);
        orderRepository.save(order);
        Set<OrderItem> orderItems = createOrderItems.toOrderItems(order,
                shoppingCart.getCartItems());
        order.setOrderItems(orderItems);
        return order;
    }

    private BigDecimal calculateTotal(ShoppingCart shoppingCart) {
        return shoppingCart.getCartItems().stream()
                .map(cartItem -> cartItem.getBook().getPrice()
                        .multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
