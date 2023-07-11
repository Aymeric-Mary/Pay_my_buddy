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

    @ManyToMany
    @JoinTable(
            name = "connection",
            joinColumns = @JoinColumn(name = "user1_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user2_id", referencedColumnName = "id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"user1_id", "user2_id"})
    )
    @Builder.Default
    private List<User> connections = new ArrayList<>();

    public void addConnection(User user) {
        if (!connections.contains(user)) {
            connections.add(user);
            user.addConnection(this);
        }
    }

    public void pay(Float amount){
        Float fee = amount * 0.005f;
        this.balance -= amount + fee;
    }
}
