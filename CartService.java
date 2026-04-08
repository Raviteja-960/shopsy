package com.shopeasy.service;

import com.shopeasy.dto.CartItemDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    private static final String CART_SESSION_KEY = "shopeasy_cart";

    @SuppressWarnings("unchecked")
    public List<CartItemDto> getCart(HttpSession session) {
        List<CartItemDto> cart = (List<CartItemDto>) session.getAttribute(CART_SESSION_KEY);
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute(CART_SESSION_KEY, cart);
        }
        return cart;
    }

    public void addToCart(HttpSession session, CartItemDto newItem) {
        List<CartItemDto> cart = getCart(session);

        // If product already in cart, increase quantity
        CartItemDto existing = cart.stream()
                .filter(i -> i.getProductId().equals(newItem.getProductId()))
                .findFirst().orElse(null);

        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + 1);
        } else {
            newItem.setQuantity(1);
            cart.add(newItem);
        }
        session.setAttribute(CART_SESSION_KEY, cart);
    }

    public void updateQuantity(HttpSession session, Long productId, int change) {
        List<CartItemDto> cart = getCart(session);
        CartItemDto item = cart.stream()
                .filter(i -> i.getProductId().equals(productId))
                .findFirst().orElse(null);

        if (item != null) {
            int newQty = item.getQuantity() + change;
            if (newQty <= 0) {
                cart.remove(item);
            } else {
                item.setQuantity(newQty);
            }
        }
        session.setAttribute(CART_SESSION_KEY, cart);
    }

    public void removeFromCart(HttpSession session, Long productId) {
        List<CartItemDto> cart = getCart(session);
        cart.removeIf(i -> i.getProductId().equals(productId));
        session.setAttribute(CART_SESSION_KEY, cart);
    }

    public void clearCart(HttpSession session) {
        session.removeAttribute(CART_SESSION_KEY);
    }

    public int getCartCount(HttpSession session) {
        return getCart(session).stream()
                .mapToInt(CartItemDto::getQuantity)
                .sum();
    }

    public double getCartTotal(HttpSession session) {
        return getCart(session).stream()
                .mapToDouble(CartItemDto::getSubtotal)
                .sum();
    }
}
