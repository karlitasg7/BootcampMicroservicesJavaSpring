package com.karla.apiusers.controller;

import com.karla.apiusers.exceptions.UserAlreadyExistsException;
import com.karla.apiusers.model.Users;
import com.karla.apiusers.repository.UsersRepository;
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

    @GetMapping
    public ResponseEntity<List<Users>> getAll() {
        List<Users> users = usersRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("{id}")
    public ResponseEntity<Users> getById(@PathVariable Long id) {
        return usersRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

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

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<Users> existingUser = usersRepository.findById(id);

        if (existingUser.isEmpty())
            return ResponseEntity.notFound().build();

        usersRepository.delete(existingUser.get());
        return ResponseEntity.ok().build();

    }

}
