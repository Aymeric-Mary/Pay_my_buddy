package com.openclassrooms.pay_my_buddy.service.user;

import com.openclassrooms.pay_my_buddy.dto.AddConnectionDto;
import com.openclassrooms.pay_my_buddy.exception.NoSuchResourceException;
import com.openclassrooms.pay_my_buddy.model.Connection;
import com.openclassrooms.pay_my_buddy.model.User;
import com.openclassrooms.pay_my_buddy.repository.UserRepository;
import com.openclassrooms.pay_my_buddy.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final AuthService authService;
    private final UserRepository userRepository;

    /**
     * Get all users except the user connected
     *
     * @return a list of users
     */
    public List<User> getConnectableUsers() {
        return userRepository.findAll()
                .stream()
                .filter(user -> !user.equals(getConnectedUser()))
                .filter(user -> getConnectedUser().getConnections().stream().noneMatch(connection -> connection.getRequestReceiver().equals(user)))
                .toList();
    }

    /**
     * Add a connection to the user connected
     *
     * @param addConnectionDto the connection to add
     */
    public void addConnection(AddConnectionDto addConnectionDto) {
        User user = getConnectedUser();
        User user2 = userRepository.findById(addConnectionDto.connectionId())
                .orElseThrow(() -> new NoSuchResourceException(User.class, addConnectionDto.connectionId()));
        user.getConnections().add(new Connection(user, user2));
    }


    /**
     * Get all connections of the user connected
     *
     * @return a list of users
     */
    public List<User> getConnections() {
        return getConnectedUser()
                .getConnections()
                .stream()
                .map(Connection::getRequestReceiver)
                .toList();
    }

    /**
     * Get a user by its id
     *
     * @param id the id of the user
     * @return the user
     */
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchResourceException(User.class, id));
    }

    private User getConnectedUser() {
        return authService.getConnectedUser();
    }

}
