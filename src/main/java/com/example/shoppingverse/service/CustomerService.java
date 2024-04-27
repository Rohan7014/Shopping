package com.example.shoppingverse.service;

import com.example.shoppingverse.dto.request.CustomerRequestDto;
import com.example.shoppingverse.dto.response.CustomerResponseDto;
import com.example.shoppingverse.model.Cart;
import com.example.shoppingverse.model.Customer;
import com.example.shoppingverse.repository.CustomerRepository;
import com.example.shoppingverse.transformer.CustomerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    public CustomerResponseDto addCustomer(CustomerRequestDto customerRequestDto) {
//        Customer customer=new Customer();customer.setName(customerRequestDto.getName());customer.setGender(customerRequestDto.getGender());customer.setEmailId(customerRequestDto.getEmailId());customer.setMobNo(customerRequestDto.getMobNo());

//        Customer customer=Customer.builder().name(customerRequestDto.getName()).gender(customerRequestDto.getGender()).emailId(customerRequestDto.getEmailId()).mobNo(customerRequestDto.getMobNo()).build();

        Customer customer= CustomerTransformer.customerRequestDtoToCustomer(customerRequestDto);

        Cart cart = new Cart();
        cart.setCartTotal(0);
        cart.setCustomer(customer);

        customer.setCart(cart);
        Customer saveCustomer =customerRepository.save(customer); //this will save both customer and cart ;

        // prepared the response dto
//        return CustomerResponseDto.builder().name(saveCustomer.getName()).gender(saveCustomer.getGender()).emailId(saveCustomer.getEmailId()).mobNo(saveCustomer.getMobNo()).build();

        return CustomerTransformer.customerToCustomerResponseDto(saveCustomer);
    }
}
