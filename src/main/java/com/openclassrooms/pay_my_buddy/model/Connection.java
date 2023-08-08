package com.openclassrooms.pay_my_buddy.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Table(name = "connection")
public class Connection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private final User requestSender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private final User requestReceiver;
}
