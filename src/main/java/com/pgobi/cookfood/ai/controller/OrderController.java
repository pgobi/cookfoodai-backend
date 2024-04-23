package com.pgobi.cookfood.ai.controller;

import com.pgobi.cookfood.ai.constants.ApplicationConstants;
import com.pgobi.cookfood.ai.entities.Order;
import com.pgobi.cookfood.ai.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "Order", description = "The API contains a method for order")
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/get")
    public ResponseEntity<List<Order>> getOrders() {
        try {
            return orderService.getOrders();
        } catch (Exception exception){
            exception.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("delete/")
    public ResponseEntity<String> deleteOrder(@RequestParam("id") Integer id) {
        try {
            return orderService.deleteOrder(id);
        } catch (Exception exception){
            exception.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApplicationConstants.UNEXPECTED_ERROR);
    }
}
