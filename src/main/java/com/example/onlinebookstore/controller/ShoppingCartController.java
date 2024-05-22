package com.example.onlinebookstore.controller;

import com.example.onlinebookstore.dto.cartitem.CartItemDto;
import com.example.onlinebookstore.dto.cartitem.CartItemResponseDto;
import com.example.onlinebookstore.dto.cartitem.CartItemUpdateQuantity;
import com.example.onlinebookstore.dto.shoppingcart.ShoppingCartDto;
import com.example.onlinebookstore.model.User;
import com.example.onlinebookstore.service.ShoppingCartService;
import com.example.onlinebookstore.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final UserService userService;

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @Operation(summary = "Get shopping cart by user", description = "Get shopping cart by user")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ShoppingCartDto getCart(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        return shoppingCartService.getCartByUser(user);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @Operation(summary = "add item to shopping cart", description = "Add item to shopping cart")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CartItemResponseDto addBookToCart(@AuthenticationPrincipal User user,
                                             @RequestBody CartItemDto request) {
        return shoppingCartService.addBookToCart(user, request.getBookId(), request.getQuantity());
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @Operation(summary = "update item quantity", description = "Update item quantity")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/cart-items/{cartItemId}")
    public CartItemResponseDto updateCartItem(@PathVariable Long cartItemId,
                                              @RequestBody CartItemUpdateQuantity request) {
        return shoppingCartService.updateCartItem(cartItemId, request.getQuantity());
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @Operation(summary = "delete item from shopping cart",
            description = "Delete item from shopping cart")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/cart-items/{cartItemId}")
    public void removeCartItem(@PathVariable Long cartItemId) {
        shoppingCartService.removeCartItem(cartItemId);
    }
}
