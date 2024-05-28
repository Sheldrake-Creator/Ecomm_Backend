package com.service;

import com.exception.ProductException;
import com.model.Product;
import com.request.CreateProductRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    public Product createProduct(CreateProductRequest request);

    public String deleteProduct(Long productId) throws ProductException;

    public Product updateProduct(Long productId, Product req) throws ProductException;

    public Product findProductById(Long id) throws ProductException;

    public Product findProductByCategory(String category) throws ProductException;

    public Page<Product>getAllProducts(String category, List<String> colors, List<String>sizes, Integer minPrice, Integer maxPrice,
                                      Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize);

    public List<Product> findAllProducts();




}
