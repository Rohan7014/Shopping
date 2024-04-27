package com.example.shoppingverse.service;

import com.example.shoppingverse.dto.request.CardRequestDto;
import com.example.shoppingverse.dto.response.CardResponseDto;
import com.example.shoppingverse.exception.CustomerNotFoundException;
import com.example.shoppingverse.model.Card;
import com.example.shoppingverse.model.Customer;
import com.example.shoppingverse.repository.CustomerRepository;
import com.example.shoppingverse.transformer.CardTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {
    @Autowired
    CustomerRepository customerRepository;
    public CardResponseDto addCard(CardRequestDto cardRequestDto){
        Customer customer=customerRepository.findByMobNo(cardRequestDto.getCustomerMobNo());
        if(customer==null) {
            throw new CustomerNotFoundException("Customer Does Not Exist");
        }
        // create card entity
        Card card= CardTransformer.CardRequestDtoToCard(cardRequestDto);
        card.setCustomer(customer);
        customer.getCards().add(card);

        Customer savedCustomer=customerRepository.save(customer);
        List<Card> cardList=savedCustomer.getCards();
        Card latestCard=cardList.get(cardList.size()-1);

        CardResponseDto cardResponseDto=CardTransformer.cardToCardResponseDto(latestCard);
        cardResponseDto.setCardNo(generateMaskedCardNo(latestCard.getCardNo()));
        return cardResponseDto;
    }
    public String generateMaskedCardNo(String cardNo){
        int cardLength=cardNo.length();
        String maskedCardNo="";
        for(int i=0;i<cardLength-4;i++){
            maskedCardNo+='X';
        }
        maskedCardNo+=cardNo.substring(cardLength-4);
        return maskedCardNo;
    }
}
