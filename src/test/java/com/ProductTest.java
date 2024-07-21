package com;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;

import org.mockito.junit.jupiter.MockitoExtension;

import com.dto.ProductDTO;
import com.model.Product;
import com.mapper.ProductMapper;

import com.repository.CategoryRepository;
import com.repository.ProductRepository;

import com.service.ProductService;
import com.service.ProductServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ProductTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private ProductMapper productMapper;

    private ProductService underTestService;

    @InjectMocks
    private com.controller.ProductController productController;

    AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTestService = new ProductServiceImpl(productRepository, categoryRepository, productMapper);
        // this.mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();

    }

    @Test
    public void getProducts_success() throws Exception {

        Long productId = 1L;
        Product mockProduct = new Product();
        mockProduct.setProductId(productId);
        mockProduct.setTitle("Jeff");
        mockProduct.setDescription("This is a good object");

        ProductDTO mockProductDTO = new ProductDTO();
        mockProductDTO.setProductId(productId);
        mockProductDTO.setTitle("Jeff");
        mockProductDTO.setDescription("This is a good object");

        // when
        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));
        // Mock the mapper to convert the product entity to DTO
        when(productMapper.toProductDTO(mockProduct)).thenReturn(mockProductDTO);

        // Act
        ProductDTO foundProduct = underTestService.findProductById(productId);
        System.out.println(foundProduct);

        // Assert
        verify(productRepository).findById(productId);
        assertNotNull(foundProduct, "The product should not be null");
        assertEquals(productId, foundProduct.getProductId(), "The product ID should match the expected value");

        // Output to verify test execution
        System.out.println("Test executed: getProducts_success");

    }

    // when
    // underTestService.findProductById(1L);
    // // then
    // verify(productRepository).findById(1L);

    // HttpResponse http = new HttpResponse();

    // HttpResponse.builder().timeStamp(LocalDateTime.now().toString()).data(Map.of("product",
    // product1))
    // .message("Product fetched
    // successfully").status(HttpStatus.OK).statusCode(HttpStatus.OK.value())
    // .build();

    // ResponseEntity<HttpResponse> entity = new ResponseEntity<HttpResponse>(http,
    // null, 200);

    // Mockito.when(productRepository.findById(1L).thenReturn(product1 ));

}

// private static List<Rating> createMockRatings(Product product) {
// List<Rating> ratings = new ArrayList<>();
// ratings.add(new Rating());
// ratings.add(new Rating());
// return ratings;
// }

// private static List<Review> createMockReviews(Product product) {
// List<Review> reviews = new ArrayList<>();
// reviews.add(new Review());
// reviews.add(new Review());
// return reviews;
// }

// private static Category createMockCategory() {
// Category category = new Category();
// category.setCategoryId(1L);
// category.setName("Accessories");
// return category;
// }

// private static Set<Size> createMockSize() {
// Set<Size> sizes = new HashSet<>();
// sizes.add(new Size("S", 1));
// sizes.add(new Size("M", 1));
// sizes.add(new Size("L", 1));
// return sizes;
// }

// // Optional<> oProduct = new Optional<>();

// Product product1 = new Product(1L, "High quality leather wallet", "Leather
// Wallet", 5000, 4500, 10, 100,
// "Premium Brand", "Brown", createMockSize(),
// "http://example.com/images/wallet.jpg", createMockRatings(null), //
// Temporarily
// // null,
// // will
// // set
// // later
// createMockReviews(null), // Temporarily null, will set later
// 150, createMockCategory(), LocalDateTime.now());

// // Update ratings and reviews with reference to the product

// Product product2 = new Product(2L, "Comfortable cotton t-shirt", "Cotton
// T-Shirt", 2000, 1800, 10, 200,
// "Fashion Brand", "White", createMockSize(),
// "http://example.com/images/tshirt.jpg", createMockRatings(null), //
// Temporarily
// // null,
// // will
// // set
// // later
// createMockReviews(null), // Temporarily null, will set later
// 250, createMockCategory(), LocalDateTime.now());
