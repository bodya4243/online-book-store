package com.example.onlinebookstore.service;

import com.example.onlinebookstore.dto.cartitem.CartItemResponseDto;
import com.example.onlinebookstore.dto.shoppingcart.ShoppingCartDto;
import com.example.onlinebookstore.model.User;

public interface ShoppingCartService {
    public ShoppingCartDto getCartByUser(User user);

    public CartItemResponseDto addBookToCart(User user, Long bookId, int quantity);

    public CartItemResponseDto updateCartItem(User user, Long cartItemId, int quantity);

    public void removeCartItem(User user, Long cartItemId);
}
