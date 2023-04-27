package com.openclassrooms.pay_my_buddy.model.user.dto.mapper;

import com.openclassrooms.pay_my_buddy.model.User;
import com.openclassrooms.pay_my_buddy.model.user.dto.UserDto;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper
public interface UserMapper {
    List<UserDto> entitiesToUserDtos(Collection<User> users);

    UserDto entityToUserDto(User user);
}
