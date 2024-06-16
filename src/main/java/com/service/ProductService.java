package com.service;

import com.dto.ProductDTO;
import com.exception.ProductException;
import com.model.Product;
import com.request.CreateProductRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    ProductDTO createProduct(CreateProductRequest request);

    String deleteProduct(Long productId) throws ProductException;

    ProductDTO updateProduct(ProductDTO req, Long productId) throws ProductException;

    Product findProductById(Long id) throws ProductException;

    ProductDTO findProductByCategory(String category) throws ProductException;

    Page<Product>getAllProducts(String category, List<String> colors, List<String> sizes, Integer minPrice, Integer maxPrice,
                                Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize);

    List<ProductDTO> findAllProducts();
}
