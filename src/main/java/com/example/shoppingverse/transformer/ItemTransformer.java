package com.example.shoppingverse.transformer;

import com.example.shoppingverse.dto.response.ItemResponseDto;
import com.example.shoppingverse.model.Item;

public class ItemTransformer {
    public static Item itemRequestDtoToItem(int requiredQuantity){
        return Item.builder()
                .requiredQuantity(requiredQuantity)
                .build();
    }
    public static ItemResponseDto ItemToItemResPonseDto(Item item){
        return ItemResponseDto.builder()
                .itemPrice(item.getProduct().getPrice())
                .itemName(item.getProduct().getProductName())
                .quantityAdded(item.getRequiredQuantity())
                .category(item.getProduct().getCategory())
                .build();
    }
}
