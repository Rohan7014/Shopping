package com.example.shoppingverse.service;

import com.example.shoppingverse.Enum.ProductStatus;
import com.example.shoppingverse.dto.request.OrderRequestDto;
import com.example.shoppingverse.dto.response.OrderResponseDto;
import com.example.shoppingverse.exception.CustomerNotFoundException;
import com.example.shoppingverse.exception.InsufficientQuantityException;
import com.example.shoppingverse.exception.InvalidCardException;
import com.example.shoppingverse.exception.ProductNotFoundException;
import com.example.shoppingverse.model.*;
import com.example.shoppingverse.repository.CardRepository;
import com.example.shoppingverse.repository.CustomerRepository;
import com.example.shoppingverse.repository.OrderRepository;
import com.example.shoppingverse.repository.ProductRepository;
import com.example.shoppingverse.transformer.ItemTransformer;
import com.example.shoppingverse.transformer.OrderTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    CardService cardService;

    public OrderResponseDto placeOrder(OrderRequestDto orderRequestDto) {
        Customer customer=customerRepository.findByEmailId(orderRequestDto.getCustomerEmail());
        if(customer==null){
            throw new CustomerNotFoundException("Customer Not Exist");
        }
        Optional<Product> productOptional=productRepository.findById(orderRequestDto.getProductId());
        if(productOptional.isEmpty()){
            throw new ProductNotFoundException("Product Not Exist");
        }
        Card card=cardRepository.findByCardNo(orderRequestDto.getCardUsed());
        Date todayDate = new Date();
        if(card==null || card.getCvv()!=orderRequestDto.getCvv() || todayDate.after(card.getValidTill())){
            throw new InvalidCardException("Invalid card");
        }
        Product product=productOptional.get();
        if(product.getAvailableQuantity()<orderRequestDto.getRequiredQuantity()){
            throw new InsufficientQuantityException("Insufficient Quantity");
        }
        int newQuantity=product.getAvailableQuantity()-orderRequestDto.getRequiredQuantity();
        product.setAvailableQuantity(newQuantity);
        if(newQuantity==0){
            product.setProductStatus(ProductStatus.OUT_OF_STOCK);
        }
        // prepare order entity
        Order_Entity orderEntity=new Order_Entity();
        orderEntity.setOrderId(String.valueOf(UUID.randomUUID()));
        orderEntity.setCardUsed(cardService.generateMaskedCardNo(orderRequestDto.getCardUsed()));
        orderEntity.setOrderTotal(orderRequestDto.getRequiredQuantity()*product.getPrice());

        Item item= ItemTransformer.itemRequestDtoToItem(orderRequestDto.getRequiredQuantity());
        item.setOrderEntity(orderEntity);
        item.setProduct(product);

        orderEntity.getItems().add(item);


        Order_Entity savedOrder=orderRepository.save(orderEntity);// save order and item
        orderEntity.setCustomer(customer);

        product.getItems().add(savedOrder.getItems().get(0));
        customer.getOrders().add(savedOrder);

       // productRepository.save(product);
       // customerRepository.save(customer);

        return OrderTransformer.orderToOrderResponseDto(savedOrder);

    }

    public Order_Entity placeOrder(Cart cart, Card card) {
        Order_Entity order=new Order_Entity();
        order.setOrderId(String.valueOf(UUID.randomUUID()));
        order.setCardUsed(cardService.generateMaskedCardNo(card.getCardNo()));

        int total=0;
        for(Item item:cart.getItems()){
            Product product=item.getProduct();
            if(product.getAvailableQuantity()<item.getRequiredQuantity()){
                throw new InsufficientQuantityException("Sorry insufficient quantity "+product.getProductName());
            }
            int newQuantity=product.getAvailableQuantity()-item.getRequiredQuantity();
            product.setAvailableQuantity(newQuantity);
            if(product.getAvailableQuantity()==0){
                product.setProductStatus(ProductStatus.OUT_OF_STOCK);
            }
            total+=product.getPrice()*item.getRequiredQuantity();
            item.setOrderEntity(order);
        }
        order.setOrderTotal(total);
        order.setItems(cart.getItems());
        order.setCustomer(card.getCustomer());
        return order;
    }
}
