package com.shopeasy.controller;

import com.shopeasy.service.CartService;
import com.shopeasy.service.ProductService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductService productService;
    private final CartService cartService;

    // ── Home / Product Listing ──
    @GetMapping({"/", "/home"})
    public String home(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort,
            HttpSession session,
            Model model) {

        var products = productService.getAllProducts();

        // Apply filters
        if (search != null && !search.isBlank()) {
            products = productService.searchProducts(search.trim());
            model.addAttribute("search", search);
        } else if (category != null && !category.equals("all")) {
            products = productService.getProductsByCategory(category);
            model.addAttribute("activeCategory", category);
        } else if (sort != null && !sort.equals("default")) {
            products = productService.getProductsSortedByPrice(sort);
            model.addAttribute("sort", sort);
        }

        model.addAttribute("products", products);
        model.addAttribute("cartCount", cartService.getCartCount(session));
        return "index";
    }
}
