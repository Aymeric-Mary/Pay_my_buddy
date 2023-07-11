package com.openclassrooms.pay_my_buddy.UT.service;


import com.openclassrooms.pay_my_buddy.dto.AddConnectionDto;
import com.openclassrooms.pay_my_buddy.exception.NoSuchResourceException;
import com.openclassrooms.pay_my_buddy.model.User;
import com.openclassrooms.pay_my_buddy.repository.UserRepository;
import com.openclassrooms.pay_my_buddy.service.UserService;
import com.openclassrooms.pay_my_buddy.service.auth.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private AuthService authServiceMock;

    private User connectedUser;

    @BeforeEach
    public void setup() {
        this.connectedUser = User.builder().id(1L).firstname("firstname").lastname("lastname").build();
        when(authServiceMock.getConnectedUser()).thenReturn(this.connectedUser);
    }

    @Nested
    class AddConnection {
        @Test
        public void testAddConnection_whenUserExist() {
            // Given
            AddConnectionDto addConnectionDto = new AddConnectionDto(2L);
            User user2 = User.builder().id(2L).build();
            when(userRepositoryMock.findById(2L)).thenReturn(Optional.of(user2));
            // When
            userService.addConnection(addConnectionDto);
            // Then
            assertThat(connectedUser.getConnections()).containsExactly(user2);
        }

        @Test
        public void testAddConnection_whenUserDoesNotExist() {
            // Given
            AddConnectionDto addConnectionDto = new AddConnectionDto(2L);
            when(userRepositoryMock.findById(2L)).thenReturn(Optional.empty());
            try {
                // When
                userService.addConnection(addConnectionDto);
                fail("Expected NoSuchResourceException to be thrown");
            } catch (NoSuchResourceException e) {
                // Then
                assertEquals(User.class, e.getResourceClass());
                assertEquals(2L, e.getId());
            }
        }
    }

    @Nested
    class GetUser{

        @BeforeEach
        public void cleanup() {
            reset(authServiceMock);
        }

        @Test
        public void testGetUser_whenUserExist() {
            // Given
            User user = User.builder().id(2L).firstname("firstname").lastname("lastname").build();
            when(userRepositoryMock.findById(2L)).thenReturn(Optional.of(user));
            // When
            User actualUser = userService.getUserById(2L);
            // Then
            assertEquals(user, actualUser);
        }

        @Test
        public void testGetUser_whenUserDoesNotExist() {
            // Given
            when(userRepositoryMock.findById(2L)).thenReturn(Optional.empty());
            try {
                // When
                userService.getUserById(2L);
                fail("Expected NoSuchResourceException to be thrown");
            } catch (NoSuchResourceException e) {
                // Then
                assertEquals(User.class, e.getResourceClass());
                assertEquals(2L, e.getId());
            }
        }
    }
}
