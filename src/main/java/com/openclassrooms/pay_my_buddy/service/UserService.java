package com.openclassrooms.pay_my_buddy.service;

import com.openclassrooms.pay_my_buddy.dto.AddConnectionDto;
import com.openclassrooms.pay_my_buddy.exception.NoSuchResourceException;
import com.openclassrooms.pay_my_buddy.mapper.UserMapper;
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
    private final UserMapper userMapper;

    /**
     * Get all users except the user connected
     * @return a list of users
     */
    public List<User> getConnectableUsers() {
        return userRepository.findConnectableUsers(getUserConnected());
    }

    /**
     * Add a connection to the user connected
     * @param addConnectionDto the connection to add
     */
    public void addConnection(AddConnectionDto addConnectionDto) {
        User user = getUserConnected();
        User user2 = userRepository.findById(addConnectionDto.connectionId())
                .orElseThrow(() -> new NoSuchResourceException(User.class, addConnectionDto.connectionId()));
        user.addConnection(user2);
    }


    /**
     * Get all connections of the user connected
     * @return a list of users
     */
    public List<User> getConnections() {
        return getUserConnected().getConnections();
    }

    /**
     * Get a user by its id
     * @param id the id of the user
     * @return the user
     */
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchResourceException(User.class, id));
    }

    private User getUserConnected() {
        return authService.getConnectedUser();
    }

}
