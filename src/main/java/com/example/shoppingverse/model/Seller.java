package com.example.shoppingverse.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "seller")
@Builder
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;
    @Column(unique = true)
    String panNo;
    @Column(unique = true)
    String emailId;
    @OneToMany(mappedBy = "seller",cascade = CascadeType.ALL)
    List<Product> products=new ArrayList<>();
}
