package org.ecommerce.productcatalogservice.mapper;

import org.ecommerce.productcatalogservice.dto.ProductResponseDto;
import org.ecommerce.productcatalogservice.model.Product;

public class ProductMapper {
    public static ProductResponseDto toProductResponseDto(Product product) {
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setProductName(product.getProductName());
        productResponseDto.setProductDescription(product.getProductDescription());
        productResponseDto.setProductPrice(product.getProductPrice());
        productResponseDto.setProductCategory(product.getProductCategory().getCategoryName());
        return productResponseDto;
    }
}
