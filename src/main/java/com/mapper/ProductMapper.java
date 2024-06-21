package com.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.dto.ProductDTO;
import com.model.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDTO toProductDTO(Product product);

    @Mapping(target = "createdAt", ignore = true)
    Product toProduct(ProductDTO productDTO);

    List<ProductDTO> toProductsDTOList(List<Product> products);
}
