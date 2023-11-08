package com.example.springboot.controllers;

import com.example.springboot.dtos.ProductRecordDTO;
import com.example.springboot.models.ProductModel;
import com.example.springboot.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@CrossOrigin("*")
@RestController
public class ProductNewController {

    @Autowired  //ponto de injeção
    ProductService productService;


    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getOneProduct(@PathVariable(value = "id") UUID id) {
        ProductModel obj = productService.findById(id);
        obj.add(linkTo(methodOn(com.example.springboot.controllers.ProductController.class).getAllProducts()).withSelfRel());

        return ResponseEntity.status(HttpStatus.OK).body(obj);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        List<ProductModel> productList = productService.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

    @PostMapping("/products")
    public ResponseEntity<ProductModel> saveProducts(@RequestBody @Valid ProductRecordDTO productRecordDTO) {
        var productModel = new ProductModel();

        //Converte de DTO para o Model
        BeanUtils.copyProperties(productRecordDTO, productModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveProduct(productModel));

    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable(value = "id") UUID id, @RequestBody @Valid ProductRecordDTO productRecordDTO) {
        var productModel = new ProductModel();
        var productO = productService.findById(id);

        if (productO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }

        BeanUtils.copyProperties(productRecordDTO, productModel);

        return ResponseEntity.status(HttpStatus.OK).body(productService.updateProduct(productModel));


    }


    @DeleteMapping("products/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id") UUID id) {
        var productModel = productService.findById(id);

        if (productModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }

        productService.deleteProduct(productModel);
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully");
    }


}


