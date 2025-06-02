package com.user.controller;

import com.user.domain.dto.UserDto;
import com.user.mapper.UserMapper;
import com.user.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    @GetMapping("/")
    public UserDto getCurrentUser() {
        return userMapper.toDto(userService.getCurrentUser());
    }

    @GetMapping("/{id}")
    public UserDto getUserById(Long id) {
        return userMapper.toDto(userService.getById(id));
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(Long id) {
        userService.deleteById(id);
    }

    @PutMapping("/{id}")
    public UserDto updateUser(Long id, UserDto userDto) {
        return userMapper.toDto(userService.update(id, userMapper.toEntity(userDto)));
    }
}