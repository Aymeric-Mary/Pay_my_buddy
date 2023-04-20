package com.openclassrooms.pay_my_buddy;

import com.openclassrooms.pay_my_buddy.model.User;
import com.openclassrooms.pay_my_buddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class DataTools {

    @Autowired
    protected UserRepository userRepository;

     protected User createUser(String email, String password) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = User.builder()
                .email(email)
                .password(encoder.encode(password))
                .build();
        return userRepository.save(user);
    }

}
