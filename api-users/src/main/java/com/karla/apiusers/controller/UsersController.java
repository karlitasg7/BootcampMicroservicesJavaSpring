package com.karla.apiusers.controller;

import com.karla.apiusers.exceptions.UserAlreadyExistsException;
import com.karla.apiusers.model.Users;
import com.karla.apiusers.repository.UsersRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController("/api/users")
public class UsersController {

    private final UsersRepository usersRepository;

    public UsersController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Operation(summary = "Get users", description = "Get the list of all users")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "Response with users list")
        }
    )
    @GetMapping
    public ResponseEntity<List<Users>> getAll() {
        List<Users> users = usersRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Get single user", description = "Get user info by Id")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "Response with user info"),
            @ApiResponse(responseCode = "404", description = "User not found")
        }
    )
    @GetMapping("{id}")
    public ResponseEntity<Users> getById(@PathVariable Long id) {
        return usersRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create user", description = "Create new user if not exists other with the same email")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "Response with new user created"),
            @ApiResponse(responseCode = "409", description = "User already exists")
        }
    )
    @PostMapping
    public ResponseEntity<Users> create(@RequestBody Users user) {

        Optional<Users> exist = usersRepository.findByEmail(user.getEmail());

        if (exist.isPresent()) {
            throw new UserAlreadyExistsException("User already exists");
        }

        Users savedUser = usersRepository.save(new Users(user.getName(), user.getEmail()));
        return ResponseEntity
            .status(201)
            .body(savedUser);
    }

    @Operation(summary = "Update user", description = "User with new information")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "Response with new user info"),
            @ApiResponse(responseCode = "409", description = "Other user exists with the same email"),
            @ApiResponse(responseCode = "404", description = "User not found")
        }
    )
    @PutMapping("{id}")
    public ResponseEntity<Users> update(@PathVariable Long id, @RequestBody Users user) {

        Optional<Users> exist = usersRepository.findByEmail(user.getEmail());

        if (exist.isPresent() && !exist.get().getId().equals(id)) {
            throw new UserAlreadyExistsException("User already exists");
        }

        return usersRepository.findById(id)
            .map(existingUsers -> {
                user.setId(existingUsers.getId());
                Users updatedUsers = usersRepository.save(user);
                return ResponseEntity.ok(updatedUsers);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete user", description = "Delete user by ID")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "User deleted"),
            @ApiResponse(responseCode = "404", description = "User not found")
        }
    )
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<Users> existingUser = usersRepository.findById(id);

        if (existingUser.isEmpty())
            return ResponseEntity.notFound().build();

        usersRepository.delete(existingUser.get());
        return ResponseEntity.ok().build();

    }

}
