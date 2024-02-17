package com.varshith.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "customer",
        uniqueConstraints ={
                @UniqueConstraint(
                        name = "unique_email",
                        columnNames = "email"
                )
        }
)
public class Customer {
    @Id()
    @SequenceGenerator(
            name = "customer_id_seq",
            sequenceName = "customer_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "customer_id_seq"
    )
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private int age;

    public Customer(String name, String email, Integer age) {
        this.name = name;
        this.email = email;
        this.age  = age;
    }
}
