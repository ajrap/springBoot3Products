package com.example.springboot.controllers;

import com.example.springboot.models.ProductModel;
import com.example.springboot.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin("*")
@RestController
public class ProductNewController {

    @Autowired  //ponto de injeção
    ProductService productService;


    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getOneProduct(@PathVariable(value = "id") UUID id) {
        ProductModel obj = productService.findById(id);

        return ResponseEntity.status(HttpStatus.OK).body(obj);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        List<ProductModel> productList = productService.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }


}


