package com.varshith.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Customer {
    @Id()
    @SequenceGenerator(
            name = "customer_sequence_id",
            sequenceName = "customer_sequence_id"
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "customer_sequence_id"
    )
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private Integer age;
}
