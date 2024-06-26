package com.example.shoppingverse.service;

import com.example.shoppingverse.dto.request.ItemRequestDto;
import com.example.shoppingverse.exception.CustomerNotFoundException;
import com.example.shoppingverse.exception.InsufficientQuantityException;
import com.example.shoppingverse.exception.ProductNotFoundException;
import com.example.shoppingverse.model.Customer;
import com.example.shoppingverse.model.Item;
import com.example.shoppingverse.model.Product;
import com.example.shoppingverse.repository.CustomerRepository;
import com.example.shoppingverse.repository.ProductRepository;
import com.example.shoppingverse.transformer.ItemTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ProductRepository productRepository;
    public Item createItem(ItemRequestDto itemRequestDto) {
        // Customer is present Or not
        Customer customer=customerRepository.findByEmailId(itemRequestDto.getCustomerEmail());
        if(customer==null){
            throw new CustomerNotFoundException("Customer does`t found");
        }
        // Product Id Exist Or Not
        Optional<Product> productOptional=productRepository.findById(itemRequestDto.getProductId1());
        if(productOptional.isEmpty()){
            throw new ProductNotFoundException("Product does`t Exist");
        }
        Product product=productOptional.get();
        // check for required quantity
        if(product.getAvailableQuantity()<itemRequestDto.getRequiredQuantity()){
            throw new InsufficientQuantityException("Sorry Required quantity not available");
        }
        Item item= ItemTransformer.itemRequestDtoToItem(itemRequestDto.getRequiredQuantity());
        return item;
    }
}
