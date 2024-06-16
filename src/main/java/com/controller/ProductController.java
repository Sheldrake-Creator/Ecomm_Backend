package com.controller;

import com.dto.ProductDTO;
import com.exception.ProductException;
import com.mapper.ProductMapper;
import com.model.Product;
import com.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ProductController {


    private ProductService productService;
    private ProductMapper productMapper;

    @GetMapping("/products")
    public ResponseEntity<Page<Product>> findProductByCategoryHandler(@RequestParam String category,
                                                                      @RequestParam List<String> color, @RequestParam List<String> size, @RequestParam Integer minPrice,
                                                                      @RequestParam Integer maxPrice, @RequestParam Integer minDiscount, @RequestParam String sort,
                                                                      @RequestParam String stock, @RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        Page<Product> res= productService.getAllProducts(
                category, color, size, minPrice, maxPrice,
                minDiscount, sort, stock,pageNumber,pageSize);
        System.out.println("complete products");
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

    @GetMapping("/products/id/{productId}")
    public ResponseEntity<ProductDTO> findProductByIdHandler(@PathVariable Long productId) throws ProductException {
        Product product = productService.findProductById(productId);
        return new ResponseEntity<ProductDTO>(productMapper.toProductDTO(product), HttpStatus.ACCEPTED);
    }
}