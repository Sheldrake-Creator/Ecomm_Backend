package com.service;

import com.dto.ProductDTO;
import com.exception.ProductServiceException;
import com.exception.ServiceException;
import com.mapper.ProductMapper;
import com.model.Category;
import com.model.Product;
import com.repository.CategoryRepository;
import com.repository.ProductRepository;
import com.request.CreateProductRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
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
    public Page<ProductDTO> findProductsByCategory(String category, List<String> brands, List<String> sizes,
            Integer minPrice, Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber,
            Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Optional<List<Product>> optionalProducts = productRepository.filterProducts(category, minPrice, maxPrice,
                minDiscount, sort);

        // Handle the case where no products are found
        List<Product> products = optionalProducts.orElse(Collections.emptyList());

        if (!brands.isEmpty()) {
            products = products.stream().filter(p -> brands.stream().anyMatch(c -> c.equalsIgnoreCase(p.getBrand())))
                    .collect(Collectors.toList());
        }
        // replace this with real/fake category search
        // Organization System
        // cat1: Real/Fake
        // cat2: Pointless/Dangerous
        // cat3: subcategory silly, dumb, mildly upsetting
        //
        // if (stock != null) {
        // if (stock.equals("in_stock")) {
        // products = products.stream().filter(p -> p.getNumInStock() >
        // 0).collect(Collectors.toList());
        // } else if (stock.equals("out_of_stock")) {
        // products = products.stream().filter(p -> p.getNumInStock() <
        // 1).collect(Collectors.toList());
        // }
        // }

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

    @Override
    public Page<ProductDTO> findAllProductsPaginated(int page) {
        int pageSize = 10;
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Product> productPage = productRepository.findAll(pageable);

        Page<ProductDTO> productDtos = productPage.map(product -> productMapper.toProductDTO(product));

        return productDtos;
    }

    @Override
    public List<ProductDTO> singleSubCategorySearch(String caseString1, String caseString2, String caseString3)
            throws ProductServiceException {
        List<ProductDTO> productDtos = new ArrayList<ProductDTO>();
        List<Product> productList = new ArrayList<Product>();
        Optional<List<Product>> productEntities = Optional.ofNullable(productList);
        System.out.println("test: " + caseString1);
        System.out.println("test: " + caseString2);
        System.out.println("test: " + caseString3);

        if (caseString3.isEmpty() || caseString2.isEmpty() || caseString1.isEmpty()) {
            // Null Scenario
            System.out.println("Null Scenario");
            throw new ProductServiceException("Empty Value Sent the Server");
        } else if (StringUtils.isNumeric(caseString3)) {
            // Category Search
            System.out.println("Category Search");
            Long categoryId = (long) Integer.parseInt(caseString3);

            productEntities = productRepository.singleSubSearch(categoryId);

        } else if (!StringUtils.isNumeric(caseString3) && (caseString3.equals("real") || caseString3.equals("fake"))) {
            System.out.println("Real/Fake Search");
            Long categoryId1;
            Long categoryId2;

            // Real or Fake
            if (caseString2.equals("pointless")) {
                if (caseString3.equals("real")) {

                    categoryId1 = 3L;
                    categoryId2 = 0L;
                    productEntities = productRepository.getByRealOrFake(categoryId1, categoryId2);

                } else {

                    categoryId1 = 5L;
                    categoryId2 = 0L;
                    productEntities = productRepository.getByRealOrFake(categoryId1, categoryId2);
                }
            } else if (caseString2.equals("dangerous")) {
                if (caseString3.equals("real")) {

                    categoryId1 = 4L;
                    categoryId2 = 0L;
                    productEntities = productRepository.getByRealOrFake(categoryId1, categoryId2);
                } else {

                    categoryId1 = 6L;
                    categoryId2 = 0L;
                    productEntities = productRepository.getByRealOrFake(categoryId1, categoryId2);
                }
            }
        } else {
            // Brand Search
            System.out.println("Brand Search");
            String brand = caseString3;
            productEntities = productRepository.findByBrand(brand);
            // Throw error if not found

        }
        return productDtos = productEntities.get().stream().map(productMapper::toProductDTO)
                .collect(Collectors.toList());

    }

    // Optional<List<Product>> products = productRepository.singleSubSearch();

    // if (products.isPresent() && !products.get().isEmpty()) {
    // productDtos =
    // products.get().stream().map(productMapper::toProductDTO).collect(Collectors.toList());

    // return productDtos;
    // }

    @Override
    public List<ProductDTO> singleBrandSearch(String brand) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'singleBrandSearch'");
    }

}