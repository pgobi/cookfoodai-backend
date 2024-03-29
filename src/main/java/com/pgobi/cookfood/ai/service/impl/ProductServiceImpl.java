package com.pgobi.cookfood.ai.service.impl;

import com.pgobi.cookfood.ai.constants.ApplicationConstants;
import com.pgobi.cookfood.ai.repository.ProductRepository;
import com.pgobi.cookfood.ai.entities.Product;
import com.pgobi.cookfood.ai.service.ProductService;
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
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ResponseEntity<String> addProduct(Map<String, String> requestMap) {
        try{
                if(this.validateProductMap(requestMap, false)){
                    productRepository.save(this.getProductFromMap(requestMap, false));
                    return ResponseService.getResponseEntity(ApplicationConstants.PRODUCT_ADD_SUCCESS, HttpStatus.OK);
                }
                return ResponseService.getResponseEntity(ApplicationConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);

        } catch (Exception exception){
            exception.printStackTrace();
        }
        return ResponseService.getResponseEntity(ApplicationConstants.UNEXPECTED_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Product>> getAllProducts() {
        try {
            return new ResponseEntity<>(productRepository.findAll(),HttpStatus.OK);
        } catch (Exception exception){
            exception.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
        try {
                if(this.validateProductMap(requestMap, true)){
                    Optional<Product> productDaoById = productRepository.findById(Integer.parseInt(requestMap.get("id")));
                    if(productDaoById.isPresent()){
                        Product productFromMap = this.getProductFromMap(requestMap, true);
                        productFromMap.setStatus(productDaoById.get().getStatus());
                        productRepository.save(productFromMap);
                        return ResponseService.getResponseEntity(ApplicationConstants.PRODUCT_UPDATE_SUCCESS + requestMap.get("id"), HttpStatus.OK);
                    } else {
                        return ResponseService.getResponseEntity(ApplicationConstants.PRODUCT_NOT_EXIST + requestMap.get("id"), HttpStatus.NOT_FOUND);
                    }
                } else return ResponseService.getResponseEntity(ApplicationConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);

        } catch (Exception exception){
            exception.printStackTrace();
        }
        return ResponseService.getResponseEntity(ApplicationConstants.UNEXPECTED_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteProduct(Integer id) {
        try {
                Optional<Product> product = productRepository.findById(id);
                if(product.isPresent()){
                    productRepository.deleteById(id);
                    return ResponseService.getResponseEntity(ApplicationConstants.PRODUCT_DELETE_SUCCESS, HttpStatus.OK);
                } else {
                    return ResponseService.getResponseEntity(ApplicationConstants.PRODUCT_NOT_EXIST + id, HttpStatus.NOT_FOUND);
                }
        } catch (Exception exception){
            exception.printStackTrace();
        }
        return ResponseService.getResponseEntity(ApplicationConstants.UNEXPECTED_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
        try {

                Optional<Product> product = productRepository.findById(Integer.parseInt(requestMap.get("id")));
                if(product.isPresent()){
                    productRepository.updateProductStatus(requestMap.get("status"), Integer.parseInt(requestMap.get("id")));
                    return ResponseService.getResponseEntity(ApplicationConstants.PRODUCT_UPDATE_SUCCESS+ requestMap.get("id"), HttpStatus.OK);
                } else {
                    return ResponseService.getResponseEntity(ApplicationConstants.PRODUCT_NOT_EXIST + requestMap.get("id"), HttpStatus.NOT_FOUND);
                }

        } catch (Exception exception){
            exception.printStackTrace();
        }
        return ResponseService.getResponseEntity(ApplicationConstants.UNEXPECTED_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Product>> getByCategory(Integer id) {
        try {
            return new ResponseEntity<>(productRepository.findProductsByCategoryId(id), HttpStatus.OK);
        } catch (Exception exception){
            exception.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<Product> getById(Integer id) {
        try {
            return new ResponseEntity<>(productRepository.getProductById(id), HttpStatus.OK);
        } catch (Exception exception){
            exception.printStackTrace();
        }
        return new ResponseEntity<>(new Product(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Product getProductFromMap(Map<String, String> requestMap, boolean isAdd) {
        Product product = new Product();
        if(isAdd){
            product.setId(Integer.parseInt(requestMap.get("id")));
        } else {
            product.setStatus("active");
        }
        product.setCategoryId(Integer.parseInt(requestMap.get("categoryId")));
        product.setName(requestMap.get("name"));
        product.setDescription(requestMap.get("description"));
        product.setPrice(Double.parseDouble(requestMap.get("price")));
        return product;
    }

    private boolean validateProductMap(Map<String, String> requestMap, boolean validateId) {
        if(requestMap.containsKey("name")){
            if(requestMap.containsKey("id") && validateId){
                return true;
            } else return !validateId;
        }
        return false;
    }
}
