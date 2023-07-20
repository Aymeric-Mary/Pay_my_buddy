package com.openclassrooms.pay_my_buddy.UT.service.auth;


import com.openclassrooms.pay_my_buddy.exception.NoSuchResourceException;
import com.openclassrooms.pay_my_buddy.model.User;
import com.openclassrooms.pay_my_buddy.repository.UserRepository;
import com.openclassrooms.pay_my_buddy.service.auth.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepositoryMock;

    @BeforeEach
    public void setup() {
        Authentication auth = new UsernamePasswordAuthenticationToken("user@mail.com", "password");
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void testGetConnectedUser_whenUserExist() {
        // Given
        User user = new User();
        user.setEmail("user@mail.com");
        when(userRepositoryMock.findByEmail("user@mail.com")).thenReturn(Optional.of(user));
        // When
        User connectedUser = authService.getConnectedUser();
        // Then
        assertThat(user).isEqualTo(connectedUser);
    }

    @Test
    public void testGetConnectedUser_whenUserDoesNotExist() {
        // Given
        when(userRepositoryMock.findByEmail("user@mail.com")).thenReturn(Optional.empty());
        try {
            // When
            authService.getConnectedUser();
            fail("Expected NoSuchResourceException to be thrown");
        } catch (NoSuchResourceException e) {
            // Then
            assertEquals(User.class, e.getResourceClass());
            assertEquals("user@mail.com", e.getId());
        }
    }
}
