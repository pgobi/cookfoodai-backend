package com.pgobi.cookfood.ai.service;

import com.pgobi.cookfood.ai.entities.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface CategoryService {
    ResponseEntity<String> addCategory(Map<String, String> requestMap);
    ResponseEntity<List<Category>> getCategories();
    ResponseEntity<String> updateCategory(Map<String, String> requestMap);
}
