package com.openclassrooms.pay_my_buddy;

import com.openclassrooms.pay_my_buddy.model.Transaction;
import com.openclassrooms.pay_my_buddy.model.User;
import com.openclassrooms.pay_my_buddy.repository.TransactionRepository;
import com.openclassrooms.pay_my_buddy.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class DataTools {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected TransactionRepository transactionRepository;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected EntityManager entityManager;

    protected User createUser(String firstname, String lastname, String email) {
        User user = User.builder()
                .firstname(firstname)
                .lastname(lastname)
                .email(email)
                .build();
        return userRepository.save(user);
    }


     protected User createUser(String email, String password) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = User.builder()
                .email(email)
                .password(encoder.encode(password))
                .build();
        return userRepository.save(user);
    }

    protected User createUser(String email) {
        return createUser(email, "test");
    }

    protected Transaction createTransaction(User sender, User receiver, Float amount, String description) {
        Transaction transaction = Transaction.builder()
                .sender(sender)
                .receiver(receiver)
                .amount(amount)
                .description(description)
                .build();
        return transactionRepository.save(transaction);
    }

}
