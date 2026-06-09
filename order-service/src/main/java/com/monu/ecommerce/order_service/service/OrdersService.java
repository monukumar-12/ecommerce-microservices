package com.monu.ecommerce.order_service.service;


import com.monu.ecommerce.order_service.clients.InventoryOpenFeignClient;
import com.monu.ecommerce.order_service.dto.OrderRequestDto;
import com.monu.ecommerce.order_service.entity.OrderItem;
import com.monu.ecommerce.order_service.entity.OrderStatus;
import com.monu.ecommerce.order_service.entity.Orders;
import com.monu.ecommerce.order_service.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrdersService {

    private final OrdersRepository orderRepository;
    private final ModelMapper modelMapper;
    private final InventoryOpenFeignClient inventoryOpenFeignClient;

    public List<OrderRequestDto> getAllOrders() {
        log.info("Fetching all orders");
        List<Orders> orders = orderRepository.findAll();
        return orders.stream().map(order -> modelMapper.map(order, OrderRequestDto.class)).toList();
    }

    public OrderRequestDto getOrderById(Long id) {
        log.info("Fetching order with ID: {}", id);
        Orders order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        return modelMapper.map(order, OrderRequestDto.class);
    }



    public OrderRequestDto createOrder(OrderRequestDto orderRequestDto) {
        log.info("Calling the createOrder method");

        Orders orders = modelMapper.map(orderRequestDto, Orders.class);

        for (OrderItem orderItem : orders.getItems()) {

            String stockStatus = inventoryOpenFeignClient.checkStock(orderItem.getProductId());

            if (stockStatus.equals("Out Of Stock")) {
                throw new RuntimeException("Product out of stock: " + orderItem.getProductId());
            }

            orderItem.setOrder(orders);
        }

        Double totalPrice = inventoryOpenFeignClient.reduceStocks(orderRequestDto);

        orders.setTotalPrice(totalPrice);
        orders.setOrderStatus(OrderStatus.CONFIRMED);

        Orders savedOrder = orderRepository.save(orders);

        return modelMapper.map(savedOrder, OrderRequestDto.class);
    }
    }


