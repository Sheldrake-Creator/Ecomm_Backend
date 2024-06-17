package com.controller;

import com.dto.ProductDTO;
import com.exception.ProductException;
import com.mapper.ProductMapper;
import com.model.Product;
import com.request.CreateProductRequest;
import com.response.APIResponse;
import com.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class AdminProductController {


    private ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<List<ProductDTO>>findAllProduct(){

        List<ProductDTO> products= productService.findAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody CreateProductRequest req) {

        ProductDTO product = productService.createProduct(req);
        return new ResponseEntity<ProductDTO>(product, HttpStatus.CREATED);
    }

    @PostMapping("/creates")
    public ResponseEntity<APIResponse> createMultipleProduct(@RequestBody CreateProductRequest[] req){

        for(CreateProductRequest product : req){
            productService.createProduct(product);
        }

        APIResponse res = new APIResponse();
        res.setMessage("product deleted");
        res.setStatus(true);

        return new ResponseEntity<>(res,HttpStatus.CREATED);
    }

    @DeleteMapping("/{productId}/delete")
    public ResponseEntity<APIResponse> deleteProduct(@PathVariable Long productId) throws ProductException {
        productService.deleteProduct(productId);
        APIResponse res = new APIResponse();
        res.setMessage("product deleted successfully");
        res.setStatus(true);
        return new ResponseEntity<>(res,HttpStatus.OK);
    }

    @PutMapping("/{productId}/update")  
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO req,@PathVariable Long productId)
            throws ProductException {
        ProductDTO product = productService.updateProduct(req, productId);
        return new ResponseEntity<ProductDTO>(product, HttpStatus.CREATED);
    }



}
