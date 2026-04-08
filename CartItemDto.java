package com.shopeasy.dto;

import lombok.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDto implements Serializable {

    private Long productId;
    private String name;
    private Double price;
    private String image;
    private Integer quantity;

    public Double getSubtotal() {
        return price * quantity;
    }
}
