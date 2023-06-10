package com.example;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
	@Autowired
	private ProductRepository repo;
	
	public Product get(String id) {
		Optional<Product> pr = repo.findById(id);
		if(pr.isPresent()) {
			return pr.get();
		}
		return null;
	}
}
