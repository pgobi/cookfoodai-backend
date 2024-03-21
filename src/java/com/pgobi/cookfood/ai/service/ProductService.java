package com.pgobi.cookfood.ai.service;

import com.pgobi.cookfood.ai.entities.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ProductService {

    ResponseEntity<String> addProduct(Map<String, String> requestMap);
    ResponseEntity<List<Product>> getAllProducts();
    ResponseEntity<String> updateProduct(Map<String, String> requestMap);
    ResponseEntity<String> deleteProduct(Integer id);
    ResponseEntity<String> updateStatus(Map<String, String> requestMap);
    ResponseEntity<List<Product>> getByCategory(Integer id);
    ResponseEntity<Product> getById(Integer id);

}
