package com.service;

import com.dto.ProductDTO;
import com.exception.ProductServiceException;
import com.request.CreateProductRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    ProductDTO createProduct(CreateProductRequest request);

    String deleteProduct(Long productId) throws ProductServiceException;

    ProductDTO updateProduct(ProductDTO req, Long productId) throws ProductServiceException;

    ProductDTO findProductById(Long id) throws ProductServiceException;

    Page<ProductDTO> findProductsByCategory(String category, List<String> colors, List<String> sizes, Integer minPrice,
            Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize)
            throws ProductServiceException;

    List<ProductDTO> findAllProducts() throws ProductServiceException;;

    Page<ProductDTO> findAllProductsPaginated(int page);

    List<ProductDTO> singleSubCategorySearch(String caseString1, String caseString2, String caseString3)
            throws ProductServiceException;

    List<ProductDTO> singleBrandSearch(String brand);
}
