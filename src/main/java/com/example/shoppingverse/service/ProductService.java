package com.example.shoppingverse.service;

import com.example.shoppingverse.Enum.ProductCategory;
import com.example.shoppingverse.dto.request.ProductRequestDto;
import com.example.shoppingverse.dto.response.ProductResponseDto;
import com.example.shoppingverse.exception.SellerNotFoundException;
import com.example.shoppingverse.model.Product;
import com.example.shoppingverse.model.Seller;
import com.example.shoppingverse.repository.ProductRepository;
import com.example.shoppingverse.repository.SellerRepository;
import com.example.shoppingverse.transformer.ProductTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    SellerRepository sellerRepository;
    public ProductResponseDto addProduct(ProductRequestDto productRequestDto){
        Seller seller=sellerRepository.findByEmailId(productRequestDto.getSellerEmail());
        if(seller==null){
            throw new SellerNotFoundException("Seller does not Exist");
        }
        // dto to entity
        Product product = ProductTransformer.ProductRequestDtoToProduct(productRequestDto);
        product.setSeller(seller);
        seller.getProducts().add(product);

        // save both
        Seller savedSeller=sellerRepository.save(seller);//Saved both product and seller
        List<Product> productList=savedSeller.getProducts();
        Product latestProduct=productList.get(productList.size()-1);
        return ProductTransformer.ProductToProductResponseDto(latestProduct);
    }

    public List<ProductResponseDto> getProductByCategoryAndPriceGreaterThan(int price,
                                                                            ProductCategory category) {
        List<Product> products=productRepository.getProductByCategoryAndPriceGreaterThan(price,category);
        List<ProductResponseDto> productResponseDtoList=new ArrayList<>();
        for(Product product:products){
            productResponseDtoList.add(ProductTransformer.ProductToProductResponseDto(product));
        }
        return productResponseDtoList;
    }
}
