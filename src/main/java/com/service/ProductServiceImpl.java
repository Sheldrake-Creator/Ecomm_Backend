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
import java.util.Arrays;
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
    public List<ProductDTO> findProductsByCategory(String category, String brand, Boolean veracity) {
        logger.info("findProductsService");
        Optional<List<Product>> optionalProducts;
        List<String> categoryArray = new ArrayList<String>();
        ArrayList<String> brandArray;
        List<Product> products = new ArrayList<Product>();

        if (category != null) {
            logger.info("CategoryNotNull");

            if (!category.isBlank()) {
                String[] splitCategories = category.split(",");
                categoryArray = Arrays.asList(splitCategories);
            }
            optionalProducts = productRepository.filterProducts(categoryArray);

            // Handle the case where no products are found
            products = optionalProducts.orElse(Collections.emptyList());
            System.out.println("Products: " + products);

            if (brand != null) {
                String[] splitBrands = brand.split(",");
                brandArray = new ArrayList<>(Arrays.asList(splitBrands));
                logger.info("brandArray {}", brandArray);
                products = products.stream()
                        .filter(p -> brandArray.stream().anyMatch(c -> c.equalsIgnoreCase(p.getBrand())))
                        .collect(Collectors.toList());
                logger.info("products2 {}", products);
            }
            if (veracity != null) {

                if (veracity) {
                    products = products.stream().filter(p -> p.getVeracity() == true).collect(Collectors.toList());
                } else {
                    products = products.stream().filter(p -> p.getVeracity() == false).collect(Collectors.toList());
                }
            }
            products.forEach(product -> System.out.println("Content: " + product.getTitle()));
        } else if (brand != null) {
            logger.info("BrandOnlySearch");
            String[] splitBrands = brand.split(",");
            brandArray = new ArrayList<>(Arrays.asList(splitBrands));
            logger.info("brandArray {}", brandArray);
            optionalProducts = productRepository.findByBrand(brandArray);
            logger.info("optionalProducts {}", optionalProducts.get());

            products = optionalProducts.orElse(Collections.emptyList());

            if (veracity != null && !products.isEmpty()) {
                if (veracity) {
                    products = products.stream().filter(product -> !product.getVeracity()).collect(Collectors.toList());
                } else {
                    products = products.stream().filter(product -> product.getVeracity()).collect(Collectors.toList());
                }
            }

        } else if (veracity != null) {
            logger.info("VeracityOnlySearch");
            if (veracity) {
                products = productRepository.getAllReal();
            } else {
                products = productRepository.getAllFake();
            }
        }
        return products.stream().map(productMapper::toProductDTO).collect(Collectors.toList());
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
    public List<ProductDTO> navContentCategorySearch(String caseString1, String caseString2, String caseString3)
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
            List<String> brand = new ArrayList<String>();
            brand.add(caseString3);
            productEntities = productRepository.findByBrand(brand);
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
    public List<ProductDTO> navContentBrandSearch(String brand) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'navContentBrandSearch'");
    }

} // Organization System
  // cat1: Real/Fake
  // cat2: Pointless/Dangerous
  // cat3: subcategory silly, dumb, mildly upsetting
  //
  // if (veracity != null) {
  // if (veracity.equals("real")) {
  // products = products.stream().filter(p -> p.getVeracity() >
  // 0).collect(Collectors.toList());
  // } else if (veracity.equals("fake")) {
  // products = products.stream().filter(p -> p.getVeracity() <
  // 1).collect(Collectors.toList());
  // }
  // }

// @Override
// public Page<ProductDTO> findProductsByCategory(String category, String brand,
// Boolean veracity, Integer pageNumber,
// Integer pageSize) {
// List<String> categoryArray = new ArrayList<String>();
// ArrayList<String> brandArray;

// Pageable pageable = PageRequest.of(0, pageSize);
// if (!category.isBlank()) {
// String[] splitCategories = category.split(",");
// categoryArray = Arrays.asList(splitCategories);
// }

// Optional<List<Product>> optionalProducts =
// productRepository.filterProducts(categoryArray);

// // Handle the case where no products are found
// List<Product> products = optionalProducts.orElse(Collections.emptyList());
// System.out.println("Products: " + products);

// if (brand != null) {
// String[] splitBrands = brand.split(",");
// brandArray = new ArrayList<>(Arrays.asList(splitBrands));
// products = products.stream()
// .filter(p -> brandArray.stream().anyMatch(c ->
// c.equalsIgnoreCase(p.getBrand())))
// .collect(Collectors.toList());
// }
// if (veracity != null) {
// if (veracity) {
// products = products.stream().filter(p -> p.getVeracity() ==
// true).collect(Collectors.toList());
// } else {
// products = products.stream().filter(p -> p.getVeracity() ==
// false).collect(Collectors.toList());
// }
// }
// System.out.println("Products2: " + products);

// if (products == null || products.isEmpty()) {
// return new PageImpl<>(Collections.emptyList(), pageable, 0);
// }

// Integer startIndex = 1;
// Integer endIndex = 10;
// System.out.println("start: " + startIndex);
// System.out.println("end: " + endIndex);

// // Ensure indices are within bounds
// if (startIndex >= products.size()) {
// startIndex = products.size();
// }
// System.out.println("Size: " + products.size());

// if (endIndex > products.size()) {
// endIndex = products.size();
// }
// System.out.println("Size2: " + endIndex);

// List<ProductDTO> productDTOs = productMapper.toProductsDTOList(products);
// System.out.println("Mapped ProductDTOs: " + productDTOs);

// List<ProductDTO> pageContent = productDTOs.subList(1, 2);
// System.out.println(pageContent.toString());
// pageContent.forEach(product -> System.out.println("Content: " +
// product.getTitle()));

// Page<ProductDTO> filteredProducts = new PageImpl<>(pageContent, pageable,
// productDTOs.size());
// System.out.println("Filtered Products: " + filteredProducts);
// return filteredProducts;
// }