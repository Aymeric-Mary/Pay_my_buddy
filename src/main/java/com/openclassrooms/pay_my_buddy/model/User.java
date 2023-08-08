package com.openclassrooms.pay_my_buddy.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "\"user\"")
public class User {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;

    private String lastname;

    private String email;

    private String password;

    private Float balance;

    private Instant creationDate;

    @OneToMany(mappedBy = "requestSender", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Connection> connections = new ArrayList<>();

    @OneToOne(mappedBy = "user")
    private BankAccount bankAccount;
}
