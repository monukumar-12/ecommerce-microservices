package com.monu.ecommerce.inventory_service.controller;


import com.monu.ecommerce.inventory_service.DTO.OrderRequestDto;
import com.monu.ecommerce.inventory_service.DTO.ProductDto;
import com.monu.ecommerce.inventory_service.clients.OrderFeignClients;
import com.monu.ecommerce.inventory_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;

    private final OrderFeignClients orderFeignClients;

    @GetMapping("/fetchOrders")
    public String fetchFromOrdersService() {
       return orderFeignClients.helloOrders();
    }


    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllInventory() {
        List<ProductDto> inventories = productService.getAllInventory();
        return ResponseEntity.ok(inventories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getInventoryById(@PathVariable Long id) {
        ProductDto inventory = productService.getProductById(id);
        return ResponseEntity.ok(inventory);
    }

    @PutMapping("/reduce-stocks")
    public ResponseEntity<Double> reduceStocks(@RequestBody OrderRequestDto orderRequestDto) {
        Double totalPrice = productService.reduceStocks(orderRequestDto);
        return ResponseEntity.ok(totalPrice);
    }

    @GetMapping("/check-stock/{productId}")
    public String checkStock(@PathVariable Long productId) {
        return productService.checkStock(productId);
    }

}
