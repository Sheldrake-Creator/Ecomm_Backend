package com.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.dto.ProductDTO;
import com.model.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "categoryId", source = "category.categoryId")
    ProductDTO toProductDTO(Product product);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "category.categoryId", source = "categoryId")
    @Mapping(target = "ratings", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    Product toProduct(ProductDTO productDTO);

    List<ProductDTO> toProductsDTOList(List<Product> products);
}
