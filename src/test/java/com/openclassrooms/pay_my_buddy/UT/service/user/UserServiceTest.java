package com.openclassrooms.pay_my_buddy.UT.service.user;


import com.openclassrooms.pay_my_buddy.dto.AddConnectionDto;
import com.openclassrooms.pay_my_buddy.exception.NoSuchResourceException;
import com.openclassrooms.pay_my_buddy.model.User;
import com.openclassrooms.pay_my_buddy.repository.UserRepository;
import com.openclassrooms.pay_my_buddy.service.auth.AuthService;
import com.openclassrooms.pay_my_buddy.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
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
    class GetConnectableUsers {
        @Test
        public void testGetConnectableUsers() {
            // Given
            List<User> connectableUsers = List.of(
                    User.builder().id(2L).build(),
                    User.builder().id(3L).build()
            );
            when(userRepositoryMock.findConnectableUsers(connectedUser)).thenReturn(connectableUsers);
            // When
            List<User> result = userService.getConnectableUsers();
            // Then
            assertThat(result).isEqualTo(connectableUsers);
        }
    }

    @Nested
    class GetConnections {
        @Test
        public void testGetConnections() {
            // Given
            List<User> connections = List.of(
                    User.builder().id(2L).build(),
                    User.builder().id(3L).build()
            );
            connectedUser.setConnections(connections);
            // When
            List<User> result = userService.getConnections();
            // Then
            assertThat(result).isEqualTo(connections);
        }
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
    class GetUserById {

        @BeforeEach
        public void cleanup() {
            reset(authServiceMock);
        }

        @Test
        public void testGetUserById_whenUserExist() {
            // Given
            User user = User.builder().id(2L).firstname("firstname").lastname("lastname").build();
            when(userRepositoryMock.findById(2L)).thenReturn(Optional.of(user));
            // When
            User actualUser = userService.getUserById(2L);
            // Then
            assertEquals(user, actualUser);
        }

        @Test
        public void testGetUserById_whenUserDoesNotExist() {
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
