package com.example.springboot.services;

import com.example.springboot.controllers.ProductController;
import com.example.springboot.dtos.ProductRecordDTO;
import com.example.springboot.exceptions.ObjectNotFoundException;
import com.example.springboot.models.ProductModel;
import com.example.springboot.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public List<ProductModel> findAll() {
        List<ProductModel> list = repository.findAll();
        if (!list.isEmpty()) {
            for (ProductModel product : list) {
                UUID id = product.getIdProduct();
                product.add(linkTo(methodOn(ProductController.class).getOneProduct(id)).withSelfRel());
            }
        }
        return list;
    }

    public ProductModel findById(UUID id) {
        Optional<ProductModel> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo:  " + ProductModel.class.getName()));
    }

    public ProductModel saveProduct(ProductModel productModel) {

        return repository.save(productModel);
    }


    public ResponseEntity<Object> updateProduct(ProductModel productModel) {

        return ResponseEntity.status(HttpStatus.OK).body(repository.save(productModel));
    }


}
