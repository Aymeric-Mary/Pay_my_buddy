package com.openclassrooms.pay_my_buddy.service.auth;

import com.openclassrooms.pay_my_buddy.exception.NoSuchResourceException;
import com.openclassrooms.pay_my_buddy.model.User;
import com.openclassrooms.pay_my_buddy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    /**
     * Get the user connected
     *
     * @return the user connected
     */
    public User getConnectedUser() {
        return getOptionalConnectedUser()
                .orElseThrow(() -> new NoSuchResourceException(User.class));
    }

    public Optional<User> getOptionalConnectedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email);
    }
}
