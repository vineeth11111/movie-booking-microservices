package com.product.mgnt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.mgnt.entity.Product;
import com.product.mgnt.repo.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductRepository repo;

	public List<Product> getAll() {
		return repo.findAll();
	}

	public Product getById(Long id) {
		return repo.findById(id).orElse(null);
	}

	public void save(Product product) {
		repo.save(product);
	}

	public void delete(Long id) {
		repo.deleteById(id);
	}
}
