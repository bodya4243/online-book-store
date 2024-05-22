package com.example.onlinebookstore.service;

import com.example.onlinebookstore.dto.cartitem.CartItemResponseDto;
import com.example.onlinebookstore.dto.shoppingcart.ShoppingCartDto;
import com.example.onlinebookstore.model.User;
import org.springframework.transaction.annotation.Transactional;

public interface ShoppingCartService {
    public ShoppingCartDto getCartByUser(User user);

    @Transactional
    public CartItemResponseDto addBookToCart(User user, Long bookId, int quantity);

    @Transactional
    public CartItemResponseDto updateCartItem(Long cartItemId, int quantity);

    @Transactional
    public void removeCartItem(Long cartItemId);

}
