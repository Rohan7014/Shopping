package com.example.shoppingverse.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "order_info")
@Builder
public class Order_Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String orderId; //Uuid
    Date orderDate;
    String cardUsed;
    int orderTotal;
    @OneToMany(mappedBy = "orderEntity",cascade = CascadeType.ALL)
    List<Item> items=new ArrayList<>();
    @ManyToOne
    @JoinColumn
    Customer customer;
}
