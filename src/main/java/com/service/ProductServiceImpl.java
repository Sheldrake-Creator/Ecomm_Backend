package com.service;

import com.dto.ProductDTO;
import com.exception.ProductException;
import com.mapper.ProductMapper;
import com.model.Category;
import com.model.Product;
import com.repository.CategoryRepository;
import com.repository.ProductRepository;
import com.request.CreateProductRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService{

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private ProductMapper productMapper;


    @Override
    public ProductDTO createProduct(CreateProductRequest req) {

        Category firstLevel=categoryRepository.findByName(req.getTopLevelCategory());

        if(firstLevel == null){
            Category firstLevelCategory = new Category();
            firstLevelCategory.setName(req.getTopLevelCategory());
            firstLevelCategory.setLevel(1);

            firstLevel=categoryRepository.save(firstLevelCategory);
        }
        Category secondLevel = categoryRepository.findByNameAndParent(req.getSecondLevelCategory()
                ,firstLevel.getName());

        if(secondLevel == null){
            Category secondLevelCategory = new Category();
            secondLevelCategory.setName(req.getSecondLevelCategory());
            secondLevelCategory.setLevel(2);

            secondLevel=categoryRepository.save(secondLevelCategory);
        }

        Category thirdLevel=categoryRepository.findByNameAndParent(req.getThirdLevelCategory(),
                secondLevel.getName());

        if(thirdLevel==null) {
            Category thirdLevelCategory = new Category();
            thirdLevelCategory.setName(req.getThirdLevelCategory());
            thirdLevelCategory.setParentCategory(secondLevel);
            thirdLevelCategory.setLevel(3);
            thirdLevel = categoryRepository.save(thirdLevelCategory);
        }

        Product product = new Product();
        product.setTitle(req.getTitle());
        product.setColor(req.getColor());
        product.setColor(req.getColor());
        product.setDescription(req.getDescription());
        product.setDiscountedPrice(req.getDiscountedPrice());
        product.setDiscountPresent(req.getDiscountPresent());
        product.setImageUrl(req.getImageUrl());
        product.setBrand(req.getBrand());
        product.setPrice(req.getPrice());
        product.setSizes(req.getSize());
        product.setNumInStock(req.getQuantity());
        product.setCategoryId(thirdLevel);
        product.setCreatedAt(LocalDateTime.now());

        Product savedProduct = productRepository.save(product);

        return productMapper.toProductDTO(savedProduct);
    }

    @Override
    public String deleteProduct(Long productId) throws ProductException {

        Product product = findProductById(productId);
        product.getSizes().clear();
        productRepository.delete(product);

        return "Product Deleted";
    }

    @Override
    public ProductDTO updateProduct(ProductDTO req, Long productId) throws ProductException {
        
        Product product = findProductById(productId);

        if(req.getNumInStock()!= 0){
            product.setNumInStock(req.getNumInStock());
        }
        productRepository.save(product);

        return productMapper.toProductDTO(product);
    }

    @Override
    public Product findProductById(Long id) throws ProductException {
        Optional<Product> product=productRepository.findById(id);
        if(product.isPresent()) {
            return product.get();
        }
        throw new ProductException ("Product not found with id - "+id);
    }

    @Override
    public ProductDTO findProductByCategory(String category) throws ProductException {
        return null;
    }

    @Override
    public Page<Product> getAllProducts(String category, List<String> colors, List<String> sizes, Integer minPrice,
                                       Integer maxPrice, Integer minDiscount, String sort, String stock,
                                       Integer pageNumber, Integer pageSize) {

        Pageable pageable= PageRequest.of(pageNumber, pageSize);

        List<Product> products=productRepository.filterProducts(category, minPrice, maxPrice, minDiscount, sort);

        if(!colors.isEmpty()) {
                products=products.stream().filter(p-> colors.stream().anyMatch(c-> c.equalsIgnoreCase(p.getColor())))
                .collect(Collectors.toList());
                //TODO
        }
        if(stock!=null) {
            if(stock.equals("in_stock")){
                products = products.stream().filter(p -> p.getNumInStock() > 0).collect(Collectors.toList());
            }
            else if (stock.equals("out_of_stock")){
                    products=products.stream().filter(p -> p.getNumInStock() < 1).collect(Collectors.toList());
            }
        }

        int startIndex=(int) pageable.getOffset();
        int endIndex=Math.min(startIndex + pageable.getPageSize(), products.size());

        List<Product> pageContent=products.subList(startIndex, endIndex);
        Page<Product> filteredProducts=new PageImpl<>(pageContent,pageable,products.size());

        return filteredProducts;
    }

    @Override
    public List<ProductDTO> findAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDtos = new ArrayList<ProductDTO>();
        
        for(Product product : products){
            productDtos.add(productMapper.toProductDTO(product));
        }
        return productDtos;
    }
}