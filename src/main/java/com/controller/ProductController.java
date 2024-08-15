package com.controller;

import com.dto.ProductDTO;
import com.exception.ProductServiceException;
import com.response.HttpResponse;
import com.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "https://silas-ecomm.com")
public class ProductController {

    private final ProductService productService;
    private final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping("/products/id/{productId}")
    public ResponseEntity<HttpResponse> findProductByIdHandler(@PathVariable Long productId) {
        try {
            ProductDTO product = productService.findProductById(productId);
            logger.debug("Product fetched: {}", product);
            return ResponseEntity.ok(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                    .data(Map.of("product", product)).message("Product fetched successfully").status(HttpStatus.OK)
                    .statusCode(HttpStatus.OK.value()).build());
        } catch (ProductServiceException e) {
            logger.error("Error fetching product by ID", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString()).message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value()).build());
        } catch (Exception e) {
            logger.error("Unexpected error fetching product by ID", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                            .message("Unexpected error fetching product").status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).build());
        }
    }

    @GetMapping("/products")
    public ResponseEntity<HttpResponse> findProductByCategory(@RequestParam String category,
            @RequestParam List<String> color, @RequestParam List<String> size, @RequestParam Integer minPrice,
            @RequestParam Integer maxPrice, @RequestParam Integer minDiscount, @RequestParam String sort,
            @RequestParam String stock, @RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        try {
            Page<ProductDTO> products = productService.findProductsByCategory(category, color, size, minPrice, maxPrice,
                    minDiscount, sort, stock, pageNumber, pageSize);
            logger.debug("Products fetched: {}", products);
            return ResponseEntity.ok(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                    .data(Map.of("products", products)).message("Products fetched successfully").status(HttpStatus.OK)
                    .statusCode(HttpStatus.OK.value()).build());
        } catch (Exception e) {
            logger.error("Unexpected error fetching products by category", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                            .message("Unexpected error fetching products").status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).build());
        }
    }

    @GetMapping("/products/all/{page}")
    public ResponseEntity<HttpResponse> findAllProduct(@PathVariable Integer page) {
        try {
            Page<ProductDTO> pages = productService.findAllProductsPaginated(page);
            logger.debug("Products retrieved: {}", pages);
            return ResponseEntity.ok(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                    .data(Map.of("pages", pages)).message("Pages retrieved").status(HttpStatus.OK)
                    .statusCode(HttpStatus.OK.value()).build());
        } catch (Exception e) {
            logger.error("Error retrieving products", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                            .message("Error retrieving products").status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).build());
        }
    }

    @GetMapping("/{caseString1}/{caseString2}/{caseString3}")
    public ResponseEntity<HttpResponse> findBySubCategory(@PathVariable String caseString1,
            @PathVariable String caseString2, @PathVariable String caseString3) {
        try {
            List<ProductDTO> products = productService.singleSubCategorySearch(caseString1, caseString2, caseString3);
            logger.debug("Products fetched: {}", products);
            return ResponseEntity.ok(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                    .data(Map.of("products", products)).message("Products fetched successfully").status(HttpStatus.OK)
                    .statusCode(HttpStatus.OK.value()).build());
        } catch (Exception e) {
            logger.error("Unexpected error fetching products by category", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                            .message("Unexpected error fetching products").status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).build());
        }
    }

}
