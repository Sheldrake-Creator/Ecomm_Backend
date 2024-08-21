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

        List<ProductDTO> findProductsByCategory(String categories, String brand, Boolean veracity)
                        throws ProductServiceException;

        List<ProductDTO> findAllProducts() throws ProductServiceException;;

        Page<ProductDTO> findAllProductsPaginated(int page);

        List<ProductDTO> navContentCategorySearch(String caseString1, String caseString2, String caseString3)
                        throws ProductServiceException;

        List<ProductDTO> navContentBrandSearch(String brand);

        // Page<ProductDTO> findProductsByCategoryPaginated(String categories, String
        // brand, Boolean veracity, Integer pageNumber,
        // Integer pageSize) throws ProductServiceException;
}
