package org.ecommerce.productcatalogservice.dto;

import lombok.Getter;
import lombok.Setter;
import org.ecommerce.productcatalogservice.model.Category;

@Getter
@Setter
public class ProductResponseDto {
    private String productName;
    private String productDescription;
    private Double productPrice;
    private String productCategory;
}
