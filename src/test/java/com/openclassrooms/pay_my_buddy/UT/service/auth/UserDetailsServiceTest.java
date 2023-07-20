package com.openclassrooms.pay_my_buddy.UT.service.auth;

import com.openclassrooms.pay_my_buddy.model.User;
import com.openclassrooms.pay_my_buddy.repository.UserRepository;
import com.openclassrooms.pay_my_buddy.service.auth.UserDetailsServiceImpl;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    void shouldLoadUserByUsername() {
        // Given
        String email = "test@test.fr";
        User expectedUser = User.builder()
                .email(email)
                .password("password")
                .build();
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(expectedUser));
        // When
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        // Then
        assertThat(expectedUser.getEmail()).isEqualTo(userDetails.getUsername());
        assertThat(expectedUser.getPassword()).isEqualTo(userDetails.getPassword());
    }

    @Test
    void shouldThrowUsernameNotFoundExceptionWhenUserDoesNotExist() {
        // Given
        String email = "test@test.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        // When
        ThrowableAssert.ThrowingCallable throwingCallable = () -> userDetailsService.loadUserByUsername(email);
        // Then
        assertThatThrownBy(throwingCallable).isInstanceOf(UsernameNotFoundException.class);
    }

}
