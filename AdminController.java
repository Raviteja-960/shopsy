package com.shopeasy.controller;

import com.shopeasy.model.Order;
import com.shopeasy.model.Product;
import com.shopeasy.service.CartService;
import com.shopeasy.service.OrderService;
import com.shopeasy.service.ProductService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ProductService productService;
    private final OrderService orderService;
    private final CartService cartService;

    // ── Admin Dashboard / Product List ──
    @GetMapping
    public String adminDashboard(HttpSession session, Model model) {
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("productCount", productService.getProductCount());
        model.addAttribute("orderCount", orderService.getOrderCount());
        model.addAttribute("totalRevenue", orderService.getTotalRevenue());
        model.addAttribute("newProduct", new Product());
        model.addAttribute("cartCount", cartService.getCartCount(session));
        return "admin";
    }

    // ── Add Product ──
    @PostMapping("/products/add")
    public String addProduct(@Valid @ModelAttribute("newProduct") Product product,
                             BindingResult result,
                             RedirectAttributes ra,
                             HttpSession session,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("products", productService.getAllProducts());
            model.addAttribute("orders", orderService.getAllOrders());
            model.addAttribute("productCount", productService.getProductCount());
            model.addAttribute("orderCount", orderService.getOrderCount());
            model.addAttribute("totalRevenue", orderService.getTotalRevenue());
            model.addAttribute("cartCount", cartService.getCartCount(session));
            return "admin";
        }
        productService.saveProduct(product);
        ra.addFlashAttribute("success", "Product \"" + product.getName() + "\" added successfully!");
        return "redirect:/admin";
    }

    // ── Delete Product ──
    @PostMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes ra) {
        productService.deleteProduct(id);
        ra.addFlashAttribute("success", "Product deleted.");
        return "redirect:/admin";
    }

    // ── Update Order Status ──
    @PostMapping("/orders/status/{id}")
    public String updateOrderStatus(@PathVariable Long id,
                                    @RequestParam String status,
                                    RedirectAttributes ra) {
        try {
            orderService.updateOrderStatus(id, Order.OrderStatus.valueOf(status));
            ra.addFlashAttribute("success", "Order #" + id + " status updated to " + status);
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("error", "Invalid status.");
        }
        return "redirect:/admin";
    }
}
