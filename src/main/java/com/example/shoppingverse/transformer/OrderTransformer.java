package com.example.shoppingverse.transformer;

import com.example.shoppingverse.dto.response.ItemResponseDto;
import com.example.shoppingverse.dto.response.OrderResponseDto;
import com.example.shoppingverse.model.Item;
import com.example.shoppingverse.model.Order_Entity;

import java.util.ArrayList;
import java.util.List;

public class OrderTransformer {
    public static OrderResponseDto orderToOrderResponseDto(Order_Entity orderEntity){
        List<ItemResponseDto> itemResponseDtoList=new ArrayList<>();
        for(Item item: orderEntity.getItems()){
            itemResponseDtoList.add(ItemTransformer.ItemToItemResPonseDto(item));
        }
        return OrderResponseDto.builder()
                .orderId(orderEntity.getOrderId())
                .orderDate(orderEntity.getOrderDate())
                .cardUsed(orderEntity.getCardUsed())
                .orderTotal(orderEntity.getOrderTotal())
                .customerName(orderEntity.getCustomer().getName())
                .items(itemResponseDtoList)
                .build();
    }
}
