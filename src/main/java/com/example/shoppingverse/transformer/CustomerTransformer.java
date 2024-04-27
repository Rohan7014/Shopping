package com.example.shoppingverse.transformer;

import com.example.shoppingverse.dto.request.CustomerRequestDto;
import com.example.shoppingverse.dto.response.CustomerResponseDto;
import com.example.shoppingverse.model.Customer;
import lombok.experimental.UtilityClass;

//@UtilityClass    for ensuring that class is not instantiated and is not std practice to write
public class CustomerTransformer {
    public static Customer customerRequestDtoToCustomer(CustomerRequestDto customerRequestDto ){
        return Customer.builder()
                .name(customerRequestDto.getName())
                .gender(customerRequestDto.getGender())
                .emailId(customerRequestDto.getEmailId())
                .mobNo(customerRequestDto.getMobNo())
                .build();
    }
    public static CustomerResponseDto customerToCustomerResponseDto(Customer customer){
        return CustomerResponseDto.builder()
                .name(customer.getName())
                .gender(customer.getGender())
                .emailId(customer.getEmailId())
                .mobNo(customer.getMobNo())
                .build();
    }
}
