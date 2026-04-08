package com.shopeasy.config;

import com.shopeasy.model.Product;
import com.shopeasy.model.User;
import com.shopeasy.repository.ProductRepository;
import com.shopeasy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        seedUsers();
        seedProducts();
    }

    private void seedUsers() {
        if (userRepository.count() > 0) return;

        userRepository.saveAll(List.of(
            User.builder()
                .email("admin@shopeasy.com")
                .password(passwordEncoder.encode("admin123"))
                .role("ROLE_ADMIN")
                .build(),
            User.builder()
                .email("user@shopeasy.com")
                .password(passwordEncoder.encode("user123"))
                .role("ROLE_USER")
                .build()
        ));
        log.info("✅ Default users seeded — admin@shopeasy.com / admin123 | user@shopeasy.com / user123");
    }

    private void seedProducts() {
        if (productRepository.count() > 0) return;

        productRepository.saveAll(List.of(
            Product.builder().name("iPhone 15 Pro").price(99999.0).category("electronics")
                .image("https://images.unsplash.com/photo-1690812041624-d3dd9baee7b4?w=400&auto=format")
                .description("Latest iPhone with A17 Pro chip and titanium design.").build(),

            Product.builder().name("Wireless Bluetooth Earbuds").price(1299.0).category("electronics")
                .image("https://images.unsplash.com/photo-1590658268037-6bf12165a8df?w=400&auto=format")
                .description("True wireless earbuds with 20hr battery life and deep bass.").build(),

            Product.builder().name("Men's Cotton T-Shirt").price(399.0).category("clothing")
                .image("https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?w=400&auto=format")
                .description("Premium soft cotton T-shirt, available in multiple colors.").build(),

            Product.builder().name("Leather Wallet").price(599.0).category("accessories")
                .image("https://images.unsplash.com/photo-1627123424574-724758594e93?w=400&auto=format")
                .description("Slim genuine leather wallet with RFID blocking.").build(),

            Product.builder().name("Smart Watch").price(2499.0).category("electronics")
                .image("https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=400&auto=format")
                .description("Fitness tracker with heart rate monitor and 7-day battery.").build(),

            Product.builder().name("Ceramic Coffee Mug").price(249.0).category("home")
                .image("https://images.unsplash.com/photo-1514228742587-6b1558fcca3d?w=400&auto=format")
                .description("350ml ceramic mug perfect for your morning coffee.").build(),

            Product.builder().name("Sunglasses UV400").price(799.0).category("accessories")
                .image("https://images.unsplash.com/photo-1572635196237-14b3f281503f?w=400&auto=format")
                .description("Stylish UV400 protection sunglasses for all seasons.").build(),

            Product.builder().name("Running Sneakers").price(1899.0).category("clothing")
                .image("https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=400&auto=format")
                .description("Lightweight running shoes with air cushion sole.").build(),

            Product.builder().name("Desk Lamp LED").price(699.0).category("home")
                .image("https://images.unsplash.com/photo-1586023492125-27b2c045efd7?w=400&auto=format")
                .description("USB-powered LED desk lamp with adjustable brightness.").build(),

            Product.builder().name("Gaming Mouse").price(299.0).category("electronics")
                .image("https://images.unsplash.com/photo-1527864550417-7fd91fc51a46?w=400&auto=format")
                .description("RGB gaming mouse with 16000 DPI sensor.").build(),

            Product.builder().name("Wireless Keyboard").price(499.0).category("electronics")
                .image("https://images.unsplash.com/photo-1587829741301-dc798b83add3?w=400&auto=format")
                .description("Mechanical wireless keyboard with RGB backlighting.").build(),

            Product.builder().name("Laptop Stand").price(399.0).category("accessories")
                .image("https://images.unsplash.com/photo-1611532736597-de2d4265fba3?w=400&auto=format")
                .description("Adjustable aluminum laptop stand for better cooling.").build(),

            Product.builder().name("Women's Handbag").price(1299.0).category("accessories")
                .image("https://images.unsplash.com/photo-1553062407-98eeb64c6a62?w=400&auto=format")
                .description("Designer faux leather handbag with multiple compartments.").build(),

            Product.builder().name("Bluetooth Speaker").price(1499.0).category("electronics")
                .image("https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=400&auto=format")
                .description("Portable speaker with 360° sound and 12hr battery.").build(),

            Product.builder().name("Yoga Mat").price(299.0).category("home")
                .image("https://images.unsplash.com/photo-1571019613454-1cb2f99b2d8b?w=400&auto=format")
                .description("Anti-slip yoga mat, 6mm thick for comfort.").build(),

            Product.builder().name("Power Bank 20000mAh").price(1199.0).category("electronics")
                .image("https://images.unsplash.com/photo-1609091839311-d5365f9ff1c5?w=400&auto=format")
                .description("Fast charging power bank with dual USB output.").build(),

            Product.builder().name("Denim Jacket").price(1799.0).category("clothing")
                .image("https://images.unsplash.com/photo-1551537482-f2075a1d41f2?w=400&auto=format")
                .description("Classic denim jacket with vintage wash.").build()
        ));
        log.info("✅ {} sample products seeded.", productRepository.count());
    }
}
