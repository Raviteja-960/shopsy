package com.shopeasy.controller;

import com.shopeasy.repository.OrderRepository;
import com.shopeasy.service.CartService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;
    private final CartService cartService;

    @GetMapping("/success/{orderId}")
    public String orderSuccess(@PathVariable Long orderId,
                               HttpSession session,
                               Model model) {
        orderRepository.findById(orderId).ifPresent(order ->
                model.addAttribute("order", order));
        model.addAttribute("cartCount", cartService.getCartCount(session));
        return "order-success";
    }
}
