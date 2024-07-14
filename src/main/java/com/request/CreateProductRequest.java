package com.request;

import com.model.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequest {

    private String title;
    private String description;
    private Integer price;
    private Integer discountedPrice;
    private Integer discountPresent;
    private Integer numInStock;
    private String brand;
    private String color;
    private final Set<Size> size = new HashSet<>();
    private String imageUrl;
    private String firstLevelCategory;
    private String secondLevelCategory;
    private String thirdLevelCategory;
}
