package com.pgobi.cookfood.ai.service;

import com.pgobi.cookfood.ai.entities.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    ResponseEntity<List<Order>> getOrders();
    ResponseEntity<String> deleteOrder(Integer id);
}
