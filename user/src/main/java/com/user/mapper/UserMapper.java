package com.user.mapper;

import com.user.domain.dto.UserDto;
import com.user.domain.model.User;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toDto(User user) {
        return UserDto.builder()
            .id(user.getId())
            .username(user.getUsername())
            .email(user.getEmail())
            .role(user.getRole().name())
            .birthDate(user.getBirthDate())
            .build();
    }

    public User toEntity(UserDto userDto) {
        return User.builder()
            .id(userDto.id())
            .username(userDto.username())
            .email(userDto.email())
            .birthDate(userDto.birthDate())
            .build();
    }
}
