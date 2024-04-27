package com.example.shoppingverse.transformer;

import com.example.shoppingverse.dto.request.SellerRequestDto;
import com.example.shoppingverse.dto.response.SellerResponseDto;
import com.example.shoppingverse.model.Seller;

public class SellerTransformer {
    public static Seller sellerRequestDtoToSeller(SellerRequestDto sellerRequestDto){
        return Seller.builder()
                .emailId(sellerRequestDto.getEmailId())
                .panNo(sellerRequestDto.getPanNo())
                .name(sellerRequestDto.getName())
                .build();
    }
    public static SellerResponseDto sellerToSellerResponseDto(Seller seller){
        return SellerResponseDto.builder()
                .emailId(seller.getEmailId())
                .name(seller.getName())
                .build();
    }
}
