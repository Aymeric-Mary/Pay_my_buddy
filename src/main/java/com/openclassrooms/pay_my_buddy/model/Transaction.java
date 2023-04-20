package com.openclassrooms.pay_my_buddy.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
public class Transaction {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private Float amount;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User receiver;

    private Instant sendDate;
}
