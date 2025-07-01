package com.product.mgnt.service;

import java.util.List;

import com.product.mgnt.entity.Product;

public interface ProductService {
    List<Product> getAll();
    Product getById(Long id);
    void save(Product product);
    void delete(Long id);
}

