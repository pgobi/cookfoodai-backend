package com.pgobi.cookfood.ai.controller;

import com.pgobi.cookfood.ai.constants.ApplicationConstants;
import com.pgobi.cookfood.ai.entities.Product;
import com.pgobi.cookfood.ai.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Tag(name = "Product", description = "The API contains a method for product")
@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_USER')")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<String> addProduct(Map<String, String> requestMap) {
        try {
            return productService.addProduct(requestMap);
        } catch (Exception exception){
            exception.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApplicationConstants.UNEXPECTED_ERROR);
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        try {
            return productService.getAllProducts();
        } catch (Exception exception){
            exception.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
        try {
            return productService.updateProduct(requestMap);
        } catch (Exception exception){
            exception.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApplicationConstants.UNEXPECTED_ERROR);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(Integer id) {
        try {
            return productService.deleteProduct(id);
        } catch (Exception exception){
            exception.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApplicationConstants.UNEXPECTED_ERROR);
    }

    @PostMapping("/status/update")
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
        try {
            return productService.updateStatus(requestMap);
        } catch (Exception exception){
            exception.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApplicationConstants.UNEXPECTED_ERROR);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<Product>> getByCategory(Integer id) {
        try {
            return productService.getByCategory(id);
        } catch (Exception exception){
            exception.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
/*
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(Integer id) {
        try {
            return productService.getById(id);
        } catch (Exception exception){
            exception.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApplicationConstants.UNEXPECTED_ERROR);
    }
*/
}

