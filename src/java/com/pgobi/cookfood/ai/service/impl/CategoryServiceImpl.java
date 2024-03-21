package com.pgobi.cookfood.ai.service.impl;


import com.google.common.base.Strings;
import com.pgobi.cookfood.ai.constants.ApplicationConstants;
import com.pgobi.cookfood.ai.repository.CategoryRepository;
import com.pgobi.cookfood.ai.entities.Category;
import com.pgobi.cookfood.ai.service.CategoryService;
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
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public ResponseEntity<String> addCategory(Map<String, String> requestMap) {
        try {
                if(this.validateCategoryMap(requestMap, false)){
                    categoryRepository.save(getCategoryFromMap(requestMap, false));
                    return ResponseService.getResponseEntity(ApplicationConstants.CATEGORY_ADD_SUCCESS, HttpStatus.OK);
                }
        } catch (Exception exception){
            exception.printStackTrace();
        }
        return ResponseService.getResponseEntity(ApplicationConstants.UNEXPECTED_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Category>> getCategories( ) {
        try {
            return new ResponseEntity<>(categoryRepository.findAll(), HttpStatus.OK);
        } catch (Exception exception){
            exception.printStackTrace();
        }
        return new ResponseEntity<List<Category>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
        try {

                if(validateCategoryMap(requestMap, true)){
                    Optional<Category> category = categoryRepository.findById(Integer.parseInt(requestMap.get("id")));
                    if(!category.isEmpty()){
                        categoryRepository.save(this.getCategoryFromMap(requestMap, true));
                        return ResponseService.getResponseEntity(ApplicationConstants.CATEGORY_UPDATE_SUCCESS + requestMap.get("id"), HttpStatus.OK);
                    } else {
                        return ResponseService.getResponseEntity(ApplicationConstants.CATEGORY_NOT_EXIST + requestMap.get("id"),HttpStatus.BAD_REQUEST);
                    }
                }
                return ResponseService.getResponseEntity(ApplicationConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
        } catch (Exception exception){
            exception.printStackTrace();
        }
        return ResponseService.getResponseEntity(ApplicationConstants.UNEXPECTED_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateCategoryMap(Map<String, String> requestMap, boolean validateId) {
        if(requestMap.containsKey("name")){
            if(requestMap.containsKey("id") && validateId){
                return true;
            }
            return !validateId;
        }
        return false;
    }
    private Category getCategoryFromMap(Map<String, String> requestMap, Boolean isAdd){
        Category category = new Category();
        if(isAdd){
            category.setId(Integer.parseInt(requestMap.get("id")));
        }
        category.setName(requestMap.get("name"));
        return category;
    }

}
