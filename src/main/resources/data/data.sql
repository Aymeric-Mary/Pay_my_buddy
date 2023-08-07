-- Création des tables
CREATE TABLE user
(
    id            BIGINT PRIMARY KEY AUTO_INCREMENT,
    firstname     VARCHAR(255) NOT NULL,
    lastname      VARCHAR(255) NOT NULL,
    email         VARCHAR(255) NOT NULL UNIQUE,
    password      VARCHAR(255) NOT NULL,
    balance       FLOAT NOT NULL DEFAULT 0.00,
    creation_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE bank_account
(
    id            BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id       BIGINT NOT NULL,
    balance       FLOAT NOT NULL DEFAULT 0.00,
    FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE connection
(
    id            BIGINT PRIMARY KEY AUTO_INCREMENT,
    user1_id      BIGINT NOT NULL,
    user2_id      BIGINT NOT NULL,
    creation_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user1_id) REFERENCES user (id),
    FOREIGN KEY (user2_id) REFERENCES user (id)
);

CREATE TABLE transaction
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    description VARCHAR(255),
    amount      FLOAT NOT NULL,
    fee         FLOAT NOT NULL,
    sender_id   BIGINT NOT NULL,
    receiver_id BIGINT NOT NULL,
    send_date   TIMESTAMP,
    FOREIGN KEY (sender_id) REFERENCES user (id),
    FOREIGN KEY (receiver_id) REFERENCES user (id)
);

-- Insertion des données
INSERT INTO user (firstname, lastname, email, password, balance, creation_date)
VALUES ('Jean', 'Dupont', 'jean.dupont@gmail.com', '$2a$12$otow12LtvKv4UDzar5c62OhmCOhBZULtW9BdcDxj5wfmKjGgxHnUm', 1000.00, '2022-04-30 10:00:00'),
       ('Marie', 'Durand', 'marie.durand@gmail.com', '$2a$12$19UOj76qQJ6gmtZqA3DegOR5iqZavsxcipapZVSHK3dnVwtihMrp.', 1500.00, '2022-08-15 14:30:00'),
       ('Pierre', 'Martin', 'pierre.martin@gmail.com', '$2a$12$WbIQXi3lcuMmBwPJMWHM5uzEa5Mjk9ZdVPq6CM3INdy1hwy0OvMvu', 2000.00, '2022-11-20 09:45:00'),
       ('Alice', 'Lefebvre', 'alice.lefebvre@gmail.com', '$2a$12$C3DpjrAoK/BJ5YEC1wUWPuUv/TY7nSe9A8.f8c/OHtqIQNZWYtPZK', 2500.00, '2022-06-07 17:20:00'),
       ('Guillaume', 'Moreau', 'guillaume.moreau@gmail.com', '$2a$12$BkL2LRztxf9W31GBc090Busx7.VF4L5Mk.DxESaYIepI8ay.6FbRK', 1800.00, '2022-10-12 11:10:00'),
       ('Sophie', 'Petit', 'sophie.petit@gmail.com', '$2a$12$EIkd6wy4QHaNc4/fQe8paOU0AU4Fje2AIqLNOGjHaz3X6dBFjsE.2', 1200.00, '2023-02-02 16:00:00'),
       ('Maxime', 'Roussel', 'maxime.roussel@gmail.com', '$2a$12$101WJciUKLS6U5efPrw60el7Zl/8Afw09aTpCKSnArX2CFRM5SA2e', 900.00, '2022-07-22 19:35:00'),
       ('Camille', 'Girard', 'camille.girard@gmail.com', '$2a$12$etfN0erphZWPRF2ZPZi2BuJwp58LisU.nKHGNEx101u7WjmWx49Ta', 2200.00, '2023-01-10 08:50:00'),
       ('Alexandre', 'Leroy', 'alexandre.leroy@gmail.com', '$2a$12$5RjOOIoB1M5jENeFuXsVd.h36GD8pK1s2WcjxVpVpiN9wlWkhMpX6', 2100.00, '2022-05-27 13:15:00'),
       ('Lucie', 'Lemaire', 'lucie.lemaire@gmail.com', '$2a$12$WqYiwgS6/AAzf6DVEK838uu3YM/0pd.nE00mO3Yt3HdTmaMAlGcOS', 1700.00, '2022-09-30 18:25:00');

INSERT INTO bank_account (user_id, balance)
VALUES (1, 1000.00),
       (2, 1500.00),
       (3, 2000.00),
       (4, 2500.00),
       (5, 1800.00),
       (6, 1200.00),
       (7, 900.00),
       (8, 2200.00),
       (9, 2100.00),
       (10, 1700.00);

INSERT INTO connection (user1_id, user2_id, creation_date)
VALUES (1, 2, '2022-05-01 15:00:00'),
       (3, 4, '2022-06-15 14:30:00'),
       (5, 6, '2022-10-15 10:20:00'),
       (7, 8, '2022-08-01 11:50:00'),
       (9, 10, '2022-10-01 09:15:00'),
       (1, 9, '2022-06-10 17:30:00'),
       (4, 7, '2022-08-30 13:45:00');

INSERT INTO transaction (description, amount, sender_id, receiver_id, send_date, fee)
VALUES ('Paiement restaurant', 50.00, 1, 2, '2022-05-02 20:00:00', 0.25),
       ('Remboursement billet train', 35.00, 2, 1, '2022-05-15 14:30:00', 0.18),
       ('Achat matériel informatique', 200.00, 3, 4, '2022-06-20 10:45:00', 1),
       ('Paiement loyer', 800.00, 5, 6, '2022-11-01 17:30:00', 4),
       ('Cadeau anniversaire', 100.00, 7, 8, '2022-08-15 21:00:00', 0.5),
       ('Participation cadeau commun', 40.00, 9, 10, '2022-10-10 19:15:00', 0.2),
       ('Transfert d\'argent', 150.00, 4, 7, '2022-09-05 14:00:00', 0.75),
       ('Paiement facture électricité', 75.00, 8, 7, '2022-09-10 16:30:00', 0.38),
       ('Remboursement frais de transport', 30.00, 6, 5, '2022-11-05 11:45:00', 0.15),
       ('Achat en ligne', 120.00, 10, 9, '2022-10-20 20:15:00', 0.6),
       ('Achat vêtements', 60.00, 1, 9, '2022-06-12 15:30:00', 0.3),
       ('Paiement abonnement sport', 45.00, 3, 4, '2022-06-25 18:00:00', 0.23),
       ('Paiement facture internet', 30.00, 4, 7, '2022-09-07 14:15:00', 0.15),
       ('Achat jeux vidéo', 50.00, 6, 5, '2022-11-08 21:45:00', 0.25);