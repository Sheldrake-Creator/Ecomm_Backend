package com.controller;

import com.dto.ProductDTO;
import com.exception.ProductException;
import com.request.CreateProductRequest;
import com.response.HttpResponse;
import com.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/products")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class AdminProductController {

    private final ProductService productService;
    Logger logger = LoggerFactory.getLogger(AdminProductController.class);

    @GetMapping("/all")
    public ResponseEntity<HttpResponse> findAllProduct() {
        try {
            List<ProductDTO> products = productService.findAllProducts();
            logger.debug("Products retrieved: {}", products);
            return ResponseEntity.ok(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                    .data(Map.of("products", products)).message("Products retrieved").status(HttpStatus.OK)
                    .statusCode(HttpStatus.OK.value()).build());
        } catch (Exception e) {
            logger.error("Error retrieving products", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                            .message("Error retrieving products").status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).build());
        }
    }

    @PostMapping("/")
    public ResponseEntity<HttpResponse> createProduct(@RequestBody CreateProductRequest req) {
        try {
            ProductDTO product = productService.createProduct(req);
            logger.debug("Product created: {}", product);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                            .data(Map.of("product", product)).message("Product created").status(HttpStatus.CREATED)
                            .statusCode(HttpStatus.CREATED.value()).build());
        } catch (Exception e) {
            logger.error("Error creating product", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                            .message("Error creating product").status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).build());
        }
    }

    @PostMapping("/creates")
    public ResponseEntity<HttpResponse> createMultipleProduct(@RequestBody CreateProductRequest[] req) {
        try {
            logger.debug("anything?");
            logger.debug("req: {} " + Arrays.toString(req));

            for (CreateProductRequest product : req) {
                productService.createProduct(product);
            }
            logger.debug("Multiple products created");
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                            .message("Multiple products added").status(HttpStatus.CREATED)
                            .statusCode(HttpStatus.CREATED.value()).build());
        } catch (Exception e) {
            logger.error("Error creating multiple products", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                            .message("Error creating multiple products").status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).build());
        }
    }

    @DeleteMapping("/{productId}/delete")
    public ResponseEntity<HttpResponse> deleteProduct(@PathVariable Long productId) {
        try {
            productService.deleteProduct(productId);
            logger.debug("Product deleted: {}", productId);
            return ResponseEntity.ok(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                    .message("Product deleted successfully").status(HttpStatus.OK).statusCode(HttpStatus.OK.value())
                    .build());
        } catch (ProductException e) {
            logger.error("Error deleting product", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                            .message("Error deleting product").status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value()).build());
        } catch (Exception e) {
            logger.error("Unexpected error deleting product", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                            .message("Unexpected error deleting product").status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).build());
        }
    }

    @PutMapping("/{productId}/update")
    public ResponseEntity<HttpResponse> updateProduct(@RequestBody ProductDTO req, @PathVariable Long productId) {
        try {
            ProductDTO product = productService.updateProduct(req, productId);
            logger.debug("Product updated: {}", product);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                            .data(Map.of("product", product)).message("Product updated").status(HttpStatus.CREATED)
                            .statusCode(HttpStatus.CREATED.value()).build());
        } catch (ProductException e) {
            logger.error("Error updating product", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                            .message("Error updating product").status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value()).build());
        } catch (Exception e) {
            logger.error("Unexpected error updating product", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(HttpResponse.builder().timeStamp(LocalDateTime.now().toString())
                            .message("Unexpected error updating product").status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).build());
        }
    }
}
