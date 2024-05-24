package com.example.onlinebookstore.service;

import com.example.onlinebookstore.dto.cartitem.CartItemResponseDto;
import com.example.onlinebookstore.dto.shoppingcart.ShoppingCartDto;
import com.example.onlinebookstore.exception.EntityNotFoundException;
import com.example.onlinebookstore.mapper.CartItemMapper;
import com.example.onlinebookstore.mapper.ShoppingCartMapper;
import com.example.onlinebookstore.model.Book;
import com.example.onlinebookstore.model.CartItem;
import com.example.onlinebookstore.model.ShoppingCart;
import com.example.onlinebookstore.model.User;
import com.example.onlinebookstore.repository.BookRepository;
import com.example.onlinebookstore.repository.CartItemRepository;
import com.example.onlinebookstore.repository.ShoppingCartRepository;
import com.example.onlinebookstore.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final CartItemMapper cartItemMapper;
    private final ShoppingCartMapper shoppingCartMapper;
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Override
    public ShoppingCartDto getCartByUser(User user) {
        ShoppingCart shoppingCart = createShoppingCart(user);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    @Transactional
    public CartItemResponseDto addBookToCart(User user, Long bookId, int quantity) {
        ShoppingCart cart = shoppingCartRepository.findByUserId(user.getId())
                .orElseThrow(()
                        -> new EntityNotFoundException("CartItem not found by Id: "
                        + user.getId()));

        Book book = bookRepository.findById(bookId).orElseThrow(()
                -> new EntityNotFoundException("Book not found by Id: " + bookId));
        CartItem cartItem = new CartItem();
        cartItem.setBook(book);
        cartItem.setQuantity(quantity);
        cartItem.setShoppingCart(cart);
        cart.getCartItems().add(cartItem);

        return cartItemMapper.toDto(cartItemRepository.save(cartItem));
    }

    @Override
    @Transactional
    public CartItemResponseDto updateCartItem(User user, Long cartItemId, int quantity) {
        checkUserExistence(user);

        CartItem cartItem = cartItemRepository
                .findById(cartItemId)
                .orElseThrow(()
                        -> new EntityNotFoundException("CartItem not found by Id: "
                        + cartItemId));
        cartItem.setQuantity(quantity);
        return cartItemMapper.toDto(cartItemRepository.save(cartItem));
    }

    @Override
    @Transactional
    public void removeCartItem(User user, Long cartItemId) {
        checkUserExistence(user);

        if (!cartItemRepository.existsById(cartItemId)) {
            throw new EntityNotFoundException("Can`t find a cartItem with id: "
                    + cartItemId);
        }
        cartItemRepository.deleteById(cartItemId);
    }

    private void checkUserExistence(User user) {
        if (!userRepository.existsById(user.getId())) {
            throw new EntityNotFoundException("Can`t find a user with id: " + user.getId());
        }
    }

    private ShoppingCart createShoppingCart(User user) {
        return shoppingCartRepository.findByUserId(user.getId())
                .orElseGet(() -> shoppingCartRepository.save(new ShoppingCart(user)));
    }
}
