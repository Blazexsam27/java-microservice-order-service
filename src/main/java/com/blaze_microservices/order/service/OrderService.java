package com.blaze_microservices.order.service;

import com.blaze_microservices.order.client.InventoryClient;
import com.blaze_microservices.order.dto.OrderRequest;
import com.blaze_microservices.order.model.Order;
import com.blaze_microservices.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    public void placeOrder(OrderRequest orderRequest){

        var isProductInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());
        if(isProductInStock){

        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setPrice(orderRequest.price());
        order.setOrderNumber(orderRequest.orderNumber());
        order.setSkucode(orderRequest.skuCode());
        order.setQuantity(orderRequest.quantity());

        orderRepository.save(order);
        }
        else{
            throw new RuntimeException("Product with SkuCode" + orderRequest.skuCode() + "is not in stock");
        }
    }

}
