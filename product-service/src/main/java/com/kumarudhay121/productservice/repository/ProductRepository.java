package com.kumarudhay121.productservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kumarudhay121.productservice.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {

}
