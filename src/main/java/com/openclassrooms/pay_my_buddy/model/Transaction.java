package com.openclassrooms.pay_my_buddy.model;

import com.openclassrooms.pay_my_buddy.Utils;
import jakarta.persistence.*;
import lombok.Builder;
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

    @Column(name = "description")
    private String description;

    @Column(name = "amount", nullable = false)
    private Float amount;

    @ManyToOne
    @JoinColumn(nullable = false, name = "sender_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_transaction_sender"))
    private User sender;

    @ManyToOne
    @JoinColumn(nullable = false, name = "receiver_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_transaction_receiver"))
    private User receiver;

    @Column(name = "send_date", nullable = false)
    @Builder.Default
    private Instant sendDate = Utils.now();
}
