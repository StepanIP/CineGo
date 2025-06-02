package com.ticket.ticketingSystem.client;

import com.ticket.ticketingSystem.dto.UserResponseDto;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "${application.config.users-url}")
public interface UserClient {

    @GetMapping("{id}")
    UserResponseDto findUserById(@PathVariable("id") Long id);
}