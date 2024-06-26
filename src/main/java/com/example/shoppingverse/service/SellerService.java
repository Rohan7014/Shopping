package com.example.shoppingverse.service;

import com.example.shoppingverse.dto.request.SellerRequestDto;
import com.example.shoppingverse.dto.response.SellerResponseDto;
import com.example.shoppingverse.model.Seller;
import com.example.shoppingverse.repository.SellerRepository;
import com.example.shoppingverse.transformer.SellerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerService {
    @Autowired
    SellerRepository sellerRepository;

    public SellerResponseDto addSeller(SellerRequestDto sellerRequestDto) {
        // dto -> Entity
        Seller seller= SellerTransformer.sellerRequestDtoToSeller(sellerRequestDto);
        // Saved Entity
        Seller savedSeller=sellerRepository.save(seller);
        // prepare Response dto
        return SellerTransformer.sellerToSellerResponseDto(savedSeller);
    }
}
