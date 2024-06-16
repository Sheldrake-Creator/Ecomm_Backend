package com.mapper;

import org.mapstruct.Mapper;

import com.dto.ProductDTO;
import com.model.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDTO toProductDTO(Product product);

    Product toProduct(ProductDTO productDTO);

}
