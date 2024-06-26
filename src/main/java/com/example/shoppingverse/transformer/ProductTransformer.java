package com.example.shoppingverse.transformer;

import com.example.shoppingverse.Enum.ProductStatus;
import com.example.shoppingverse.dto.request.ProductRequestDto;
import com.example.shoppingverse.dto.response.ProductResponseDto;
import com.example.shoppingverse.model.Product;
import com.example.shoppingverse.service.ProductService;

public class ProductTransformer {
    public static Product ProductRequestDtoToProduct(ProductRequestDto productRequestDto){
        return Product.builder()
                .productName(productRequestDto.getProductName())
                .price(productRequestDto.getPrice())
                .availableQuantity(productRequestDto.getAvailableQuantity())
                .category(productRequestDto.getCategory())
                .productStatus(ProductStatus.AVAILABLE)
                .build();
    }
    public static ProductResponseDto ProductToProductResponseDto(Product product){
        return ProductResponseDto.builder()
                .sellerName(product.getSeller().getName())
                .productName(product.getProductName())
                .availableQuantity(product.getAvailableQuantity())
                .price(product.getPrice())
                .productStatus(product.getProductStatus())
                .category(product.getCategory())
                .build();
    }
}
