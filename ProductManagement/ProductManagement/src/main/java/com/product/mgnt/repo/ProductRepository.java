package com.product.mgnt.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.product.mgnt.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

