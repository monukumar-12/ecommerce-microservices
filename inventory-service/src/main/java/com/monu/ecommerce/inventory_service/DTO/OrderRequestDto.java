package com.monu.ecommerce.inventory_service.DTO;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDto extends OrderRequestItemDto {

    private List<OrderRequestDto> items;
}
