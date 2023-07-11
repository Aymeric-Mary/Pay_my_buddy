package com.openclassrooms.pay_my_buddy.mapper;

import com.openclassrooms.pay_my_buddy.dto.UserDto;
import com.openclassrooms.pay_my_buddy.model.User;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper
public interface UserMapper {
    List<UserDto> entitiesToUserDtos(Collection<User> users);

    UserDto entityToUserDto(User user);
}
