package com.shopeasy.controller;

import com.shopeasy.dto.CartItemDto;
import com.shopeasy.model.Product;
import com.shopeasy.service.CartService;
import com.shopeasy.service.ProductService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final ProductService productService;

    // ── View Cart ──
    @GetMapping
    public String viewCart(HttpSession session, Model model) {
        model.addAttribute("cartItems", cartService.getCart(session));
        model.addAttribute("cartTotal", cartService.getCartTotal(session));
        model.addAttribute("cartCount", cartService.getCartCount(session));
        return "cart";
    }

    // ── Add to Cart ──
    @PostMapping("/add/{productId}")
    public String addToCart(@PathVariable Long productId,
                            HttpSession session,
                            RedirectAttributes ra) {

        Product product = productService.getProductById(productId)
                .orElse(null);

        if (product == null) {
            ra.addFlashAttribute("error", "Product not found.");
            return "redirect:/";
        }

        CartItemDto item = CartItemDto.builder()
                .productId(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .image(product.getImage())
                .quantity(1)
                .build();

        cartService.addToCart(session, item);
        ra.addFlashAttribute("toast", "\"" + product.getName() + "\" added to cart!");
        return "redirect:/";
    }

    // ── Update Quantity ──
    @PostMapping("/update/{productId}")
    public String updateQuantity(@PathVariable Long productId,
                                 @RequestParam int change,
                                 HttpSession session) {
        cartService.updateQuantity(session, productId, change);
        return "redirect:/cart";
    }

    // ── Remove Item ──
    @PostMapping("/remove/{productId}")
    public String removeItem(@PathVariable Long productId, HttpSession session) {
        cartService.removeFromCart(session, productId);
        return "redirect:/cart";
    }
}
