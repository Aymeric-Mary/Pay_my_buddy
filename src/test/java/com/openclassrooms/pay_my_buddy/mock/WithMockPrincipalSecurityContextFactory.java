package com.openclassrooms.pay_my_buddy.mock;

import com.openclassrooms.pay_my_buddy.model.User;
import com.openclassrooms.pay_my_buddy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;

@RequiredArgsConstructor
public class WithMockPrincipalSecurityContextFactory implements WithSecurityContextFactory<WithMockPrincipal> {

    private final UserRepository userRepository;

    @Override
    public SecurityContext createSecurityContext(WithMockPrincipal principal) {
        SecurityContext context = SecurityContextHolder.getContext();
        createUser(principal.id(), principal.email());
        Authentication auth = new UsernamePasswordAuthenticationToken(principal.email(), null, List.of());
        context.setAuthentication(auth);
        return context;
    }

    private User createUser(long id, String email) {
        return userRepository.save(
                User.builder()
                        .id(id)
                        .email(email)
                        .build()
        );
    }
}