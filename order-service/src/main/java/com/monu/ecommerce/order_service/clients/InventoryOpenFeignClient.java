package com.monu.ecommerce.order_service.clients;


import com.monu.ecommerce.order_service.dto.OrderRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "INVENTORY-SERVICE", path = "/inventory/products")
public interface InventoryOpenFeignClient {

    @PutMapping("/reduce-stocks")
    Double reduceStocks(@RequestBody OrderRequestDto orderRequestDto);


    @GetMapping("/check-stock/{productId}")
    String checkStock(@PathVariable Long productId);
}