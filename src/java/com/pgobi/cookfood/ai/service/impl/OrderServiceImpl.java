package com.pgobi.cookfood.ai.service.impl;


import com.pgobi.cookfood.ai.constants.ApplicationConstants;
import com.pgobi.cookfood.ai.entities.Order;
import com.pgobi.cookfood.ai.repository.OrderRepository;
import com.pgobi.cookfood.ai.service.OrderService;
import com.pgobi.cookfood.ai.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private void addOrder(Map<String, Object> requestMap) {
        try {
            Order order = new Order();
            order.setName((String) requestMap.get("name"));
            order.setUuid((String) requestMap.get("uuid"));
            order.setEmail((String) requestMap.get("email"));
            order.setPaymentMethod((String) requestMap.get("paymentMethod"));
            order.setTotalPrice(Double.parseDouble((String) requestMap.get("totalAmount")));
            order.setProductDetail((String) requestMap.get("productDetails"));
            orderRepository.save(order);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public ResponseEntity<List<Order>> getOrders() {
        List<Order> orders = new ArrayList<>();
        try {
            orders = orderRepository.findAll();

        } catch (Exception exception){
            exception.printStackTrace();
        }
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteOrder(Integer id) {
        try {
            Optional<Order> orderOptional = orderRepository.findById(id);
            if(orderOptional.isPresent()){
                orderRepository.deleteById(id);
                return ResponseService.getResponseEntity(ApplicationConstants.ORDER_DELETE_SUCCESS + id, HttpStatus.OK);
            } else {
                return ResponseService.getResponseEntity(ApplicationConstants.ORDER_NOT_EXIST + id, HttpStatus.NOT_FOUND);
            }
        } catch (Exception exception){
            exception.printStackTrace();
        }
        return ResponseService.getResponseEntity(ApplicationConstants.UNEXPECTED_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
