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

    public List<UserDto> getAllNotConnectedUsers() {
        List<User> users = userRepository.findByConnectionsNotContaining(getUserConnected());
        return userMapper.entitiesToUserDtos(users);
    }

    public void addConnection(AddConnectionDto addConnectionDto) {
        User user = getUserConnected();
        User user2 = userRepository.findById(addConnectionDto.getConnectionId())
                .orElseThrow(() -> new NoSuchResourceException(User.class, addConnectionDto.getConnectionId()));
        user.addConnection(user2);
    }

    public List<UserDto> getConnections() {
        Set<User> connections = getUserConnected().getConnections();
        return userMapper.entitiesToUserDtos(connections);
    }

    public User getUserConnected() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email).orElseThrow(() -> new NoSuchResourceException(User.class, email));
    }

}
