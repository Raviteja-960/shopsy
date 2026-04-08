package com.shopeasy.controller;

import com.shopeasy.dto.CheckoutRequest;
import com.shopeasy.model.Order;
import com.shopeasy.service.CartService;
import com.shopeasy.service.OrderService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/checkout")
@RequiredArgsConstructor
public class CheckoutController {

    private final CartService cartService;
    private final OrderService orderService;

    // ── Show Checkout Page ──
    @GetMapping
    public String showCheckout(HttpSession session, Model model) {
        var cart = cartService.getCart(session);
        if (cart.isEmpty()) return "redirect:/cart";

        model.addAttribute("cartItems", cart);
        model.addAttribute("cartTotal", cartService.getCartTotal(session));
        model.addAttribute("cartCount", cartService.getCartCount(session));
        model.addAttribute("checkoutRequest", new CheckoutRequest());
        return "checkout";
    }

    // ── Place Order ──
    @PostMapping("/place")
    public String placeOrder(@Valid @ModelAttribute CheckoutRequest checkoutRequest,
                             BindingResult result,
                             HttpSession session,
                             Model model) {

        var cart = cartService.getCart(session);
        if (cart.isEmpty()) return "redirect:/cart";

        // Validation errors → back to checkout
        if (result.hasErrors()) {
            model.addAttribute("cartItems", cart);
            model.addAttribute("cartTotal", cartService.getCartTotal(session));
            model.addAttribute("cartCount", cartService.getCartCount(session));
            return "checkout";
        }

        // Save order
        Order order = orderService.placeOrder(checkoutRequest, cart);

        // Clear cart after successful order
        cartService.clearCart(session);

        return "redirect:/order/success/" + order.getId();
    }
}
