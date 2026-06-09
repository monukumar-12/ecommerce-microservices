package com.monu.ecommerce.inventory_service.DTO;


import lombok.Data;

@Data
public class OrderRequestItemDto {
    private Long productId;
    private Integer quantity;
}