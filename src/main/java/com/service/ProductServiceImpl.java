package com.service;

import com.dto.ProductDTO;
import com.exception.ProductException;
import com.mapper.ProductMapper;
import com.model.Category;
import com.model.Product;
import com.repository.CategoryRepository;
import com.repository.ProductRepository;
import com.request.CreateProductRequest;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;
    private final Logger logger = LoggerFactory.getLogger(RatingServiceImpl.class);

    @Override
    public ProductDTO createProduct(CreateProductRequest req) {

        Optional<Category> oFirstLevel = categoryRepository.findByName(req.getTopLevelCategory());

        Category firstLevel = oFirstLevel.orElseGet(() -> {
            Category firstLevelCategory = new Category();
            firstLevelCategory.setName(req.getTopLevelCategory());
            firstLevelCategory.setLevel(1);
            return categoryRepository.save(firstLevelCategory);
        });

        Optional<Category> oSecondLevel = categoryRepository.findByNameAndParent(req.getSecondLevelCategory(),
                firstLevel.getName());

        Category secondLevel = oSecondLevel.orElseGet(() -> {
            Category secondLevelCategory = new Category();
            secondLevelCategory.setName(req.getSecondLevelCategory());
            secondLevelCategory.setLevel(2);
            secondLevelCategory.setParentCategory(firstLevel);
            return categoryRepository.save(secondLevelCategory);
        });

        Optional<Category> oThirdLevel = categoryRepository.findByNameAndParent(req.getThirdLevelCategory(),
                secondLevel.getName());

        Category thirdLevel = oThirdLevel.orElseGet(() -> {
            Category thirdLevelCategory = new Category();
            thirdLevelCategory.setName(req.getThirdLevelCategory());
            thirdLevelCategory.setLevel(3);
            thirdLevelCategory.setParentCategory(secondLevel);
            return categoryRepository.save(thirdLevelCategory);
        });

        Product product = new Product();
        product.setTitle(req.getTitle());
        product.setColor(req.getColor());
        product.setDescription(req.getDescription());
        product.setDiscountedPrice(req.getDiscountedPrice());
        product.setDiscountPresent(req.getDiscountPresent());
        product.setImageUrl(req.getImageUrl());
        product.setBrand(req.getBrand());
        product.setPrice(req.getPrice());
        product.setSizes(req.getSize());
        product.setNumInStock(req.getQuantity());
        product.setCategory(thirdLevel);
        product.setCreatedAt(LocalDateTime.now());

        Product savedProduct = productRepository.save(product);

        return productMapper.toProductDTO(savedProduct);
    }

    @Override
    public String deleteProduct(Long productId) throws ProductException {

        ProductDTO product = findProductById(productId);
        product.getSizes().clear();
        productRepository.delete(productMapper.toProduct(product));

        return "Product Deleted";
    }

    @Override
    public ProductDTO updateProduct(ProductDTO req, Long productId) throws ProductException {

        ProductDTO product = findProductById(productId);

        if (req.getNumInStock() != 0) {
            product.setNumInStock(req.getNumInStock());
        }
        productRepository.save(productMapper.toProduct(product));

        return product;
    }

    @Override
    public ProductDTO findProductById(Long id) throws ProductException {
        // Optional<Product> productEntity=

        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            ProductDTO productDto = productMapper.toProductDTO(product.get());
            return productDto;
        }

        throw new ProductException("Product not found with id - " + id);
    }

    @Override
    public ProductDTO findProductByCategory(String category) throws ProductException {
        return null;
    }

    @Override
    public Page<ProductDTO> getAllProducts(String category, List<String> colors, List<String> sizes, Integer minPrice,
            Integer maxPrice, Integer minDiscount, String sort, String stock,
            Integer pageNumber, Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Optional<List<Product>> optionalProducts = productRepository.filterProducts(category, minPrice, maxPrice,
                minDiscount, sort);

        // Handle the case where no products are found
        List<Product> products = optionalProducts.orElse(Collections.emptyList());

        if (!colors.isEmpty()) {
            products = products.stream()
                    .filter(p -> colors.stream().anyMatch(c -> c.equalsIgnoreCase(p.getColor())))
                    .collect(Collectors.toList());
        }

        if (stock != null) {
            if (stock.equals("in_stock")) {
                products = products.stream()
                        .filter(p -> p.getNumInStock() > 0)
                        .collect(Collectors.toList());
            } else if (stock.equals("out_of_stock")) {
                products = products.stream()
                        .filter(p -> p.getNumInStock() < 1)
                        .collect(Collectors.toList());
            }
        }

        int startIndex = (int) pageable.getOffset();
        int endIndex = Math.min(startIndex + pageable.getPageSize(), products.size());

        List<ProductDTO> productDTOs = productMapper.toProductsDTOList(products);
        List<ProductDTO> pageContent = productDTOs.subList(startIndex, endIndex);

        Page<ProductDTO> filteredProducts = new PageImpl<>(pageContent, pageable, productDTOs.size());

        return filteredProducts;
    }

    @Override
    public List<ProductDTO> findAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDtos = new ArrayList<ProductDTO>();

        for (Product product : products) {
            productDtos.add(productMapper.toProductDTO(product));
        }
        return productDtos;
    }
}