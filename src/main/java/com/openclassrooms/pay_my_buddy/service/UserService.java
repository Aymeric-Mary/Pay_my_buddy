package com.openclassrooms.pay_my_buddy.service;

import com.openclassrooms.pay_my_buddy.exception.NoSuchResourceException;
import com.openclassrooms.pay_my_buddy.model.User;
import com.openclassrooms.pay_my_buddy.model.user.dto.AddConnectionDto;
import com.openclassrooms.pay_my_buddy.model.user.dto.UserDto;
import com.openclassrooms.pay_my_buddy.model.user.dto.mapper.UserMapper;
import com.openclassrooms.pay_my_buddy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Get all users except the user connected
     * @return a list of users
     */
    public List<UserDto> getConnectableUsers() {
        List<User> users = userRepository.findConnectableUsers(getUserConnected());
        return userMapper.entitiesToUserDtos(users);
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
    public List<UserDto> getConnections() {
        Set<User> connections = getUserConnected().getConnections();
        return userMapper.entitiesToUserDtos(connections);
    }

    /**
     * Get the user connected
     * @return the user connected
     */
    public User getUserConnected() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email).orElseThrow(() -> new NoSuchResourceException(User.class, email));
    }

    /**
     * Get a user by its id
     * @param id the id of the user
     * @return the user
     */
    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchResourceException(User.class, id));
    }

}
