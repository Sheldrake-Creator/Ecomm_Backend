package com.service;

import com.dto.ProductDTO;
import com.exception.ProductServiceException;
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
    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    public ProductDTO createProduct(CreateProductRequest req) {

        // Fetch or create top-level category
        Category firstLevel = categoryRepository.findByName(req.getFirstLevelCategory()).orElseGet(() -> {
            Category firstLevelCategory = new Category();
            firstLevelCategory.setName(req.getFirstLevelCategory());
            firstLevelCategory.setLevel(1);
            return categoryRepository.save(firstLevelCategory);
        });
        logger.debug("firstLevel: {}", firstLevel);

        // Fetch or create second-level category
        Category secondLevel = categoryRepository
                .findByNameAndParent(req.getSecondLevelCategory(), firstLevel.getName()).orElseGet(() -> {
                    Category secondLevelCategory = new Category();
                    secondLevelCategory.setName(req.getSecondLevelCategory());
                    secondLevelCategory.setLevel(2);
                    secondLevelCategory.setParentCategory(firstLevel);
                    return categoryRepository.save(secondLevelCategory);
                });
        logger.debug("secondLevel: {}", secondLevel);

        // Fetch or create third-level category
        Category thirdLevel = categoryRepository.findByNameAndParent(req.getThirdLevelCategory(), secondLevel.getName())
                .orElseGet(() -> {
                    Category thirdLevelCategory = new Category();
                    thirdLevelCategory.setName(req.getThirdLevelCategory());
                    thirdLevelCategory.setLevel(3);
                    thirdLevelCategory.setParentCategory(secondLevel);
                    return categoryRepository.save(thirdLevelCategory);
                });
        logger.debug("thirdLevel: {}", thirdLevel);

        Product product = Product.builder().title(req.getTitle()).color(req.getColor())
                .description(req.getDescription()).discountedPrice(req.getDiscountedPrice())
                .discountPresent(req.getDiscountPresent()).imageUrl(req.getImageUrl()).brand(req.getBrand())
                .price(req.getPrice()).sizes(req.getSize()).numInStock(req.getNumInStock()).category(thirdLevel)
                .createdAt(LocalDateTime.now()).build();

        logger.debug("productEntity: {}", product);

        Product savedProduct = productRepository.save(product);

        logger.debug("savedProduct: {}", savedProduct);

        return productMapper.toProductDTO(savedProduct);
    }

    @Override
    public String deleteProduct(Long productId) throws ProductServiceException {

        ProductDTO product = findProductById(productId);
        product.getSizes().clear();
        productRepository.delete(productMapper.toProduct(product));

        return "Product Deleted";
    }

    @Override
    public ProductDTO updateProduct(ProductDTO req, Long productId) throws ProductServiceException {

        ProductDTO product = findProductById(productId);

        if (req.getNumInStock() != 0) {
            product.setNumInStock(req.getNumInStock());
        }
        productRepository.save(productMapper.toProduct(product));

        return product;
    }

    @Override
    public ProductDTO findProductById(Long id) throws ProductServiceException {
        logger.debug("ProductId: {}", id);

        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            ProductDTO productDto = productMapper.toProductDTO(product.get());
            logger.debug("ProductDTO: {}", productDto);
            return productDto;
        }

        throw new ProductServiceException("Product not found with id - " + id);
    }

    @Override
    public ProductDTO findProductByCategory(String category) throws ProductServiceException {
        return null;
    }

    @Override
    public Page<ProductDTO> getAllProducts(String category, List<String> colors, List<String> sizes, Integer minPrice,
            Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Optional<List<Product>> optionalProducts = productRepository.filterProducts(category, minPrice, maxPrice,
                minDiscount, sort);

        // Handle the case where no products are found
        List<Product> products = optionalProducts.orElse(Collections.emptyList());

        if (!colors.isEmpty()) {
            products = products.stream().filter(p -> colors.stream().anyMatch(c -> c.equalsIgnoreCase(p.getColor())))
                    .collect(Collectors.toList());
        }

        if (stock != null) {
            if (stock.equals("in_stock")) {
                products = products.stream().filter(p -> p.getNumInStock() > 0).collect(Collectors.toList());
            } else if (stock.equals("out_of_stock")) {
                products = products.stream().filter(p -> p.getNumInStock() < 1).collect(Collectors.toList());
            }
        }

        Integer startIndex = (int) pageable.getOffset();
        Integer endIndex = Math.min(startIndex + pageable.getPageSize(), products.size());

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