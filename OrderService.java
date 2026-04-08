package com.shopeasy.service;

import com.shopeasy.dto.CartItemDto;
import com.shopeasy.dto.CheckoutRequest;
import com.shopeasy.model.Order;
import com.shopeasy.model.OrderItem;
import com.shopeasy.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public Order placeOrder(CheckoutRequest request, List<CartItemDto> cartItems) {

        // Build the order
        Order order = Order.builder()
                .customerName(request.getCustomerName())
                .customerPhone(request.getCustomerPhone())
                .customerEmail(request.getCustomerEmail())
                .address(request.getAddress())
                .city(request.getCity())
                .pincode(request.getPincode())
                .paymentMethod(request.getPaymentMethod())
                .totalAmount(cartItems.stream().mapToDouble(CartItemDto::getSubtotal).sum())
                .status(Order.OrderStatus.PENDING)
                .build();

        // Build order items
        List<OrderItem> items = cartItems.stream().map(c -> OrderItem.builder()
                .order(order)
                .productId(c.getProductId())
                .productName(c.getName())
                .price(c.getPrice())
                .quantity(c.getQuantity())
                .image(c.getImage())
                .build()
        ).collect(Collectors.toList());

        order.setItems(items);
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAllByOrderByOrderedAtDesc();
    }

    public long getOrderCount() {
        return orderRepository.count();
    }

    public double getTotalRevenue() {
        return orderRepository.findAll().stream()
                .mapToDouble(Order::getTotalAmount).sum();
    }

    public void updateOrderStatus(Long orderId, Order.OrderStatus status) {
        orderRepository.findById(orderId).ifPresent(order -> {
            order.setStatus(status);
            orderRepository.save(order);
        });
    }
}
