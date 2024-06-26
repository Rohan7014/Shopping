package com.example.shoppingverse.model;

import ch.qos.logback.core.joran.spi.NoAutoStart;
import com.example.shoppingverse.Enum.Gender;
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
@Table(name = "customer")
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;
    @Column(unique = true)
    String emailId;
    @Column(unique = true)
    String mobNo;
    @Enumerated(EnumType.STRING)
    Gender gender;
    @OneToOne(mappedBy = "customer",cascade = CascadeType.ALL)
    Cart cart;
    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    List<Card> cards=new ArrayList<>();
    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
    List<Order_Entity> orders=new ArrayList<>();
}
