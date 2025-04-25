package com.karla.apibooks.service;

import com.karla.apibooks.dto.UserDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("/api/users")
public interface UsersService {

    @GetExchange("/{id}")
    UserDTO getById(@PathVariable Long id);
}
