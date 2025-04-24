package com.karla.apiusers.init;

import com.karla.apiusers.model.Users;
import com.karla.apiusers.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(UsersRepository usersRepository) {
        return args -> {
            if (usersRepository.count() == 0) {
                List<Users> usersList = Arrays.asList(
                    new Users("karla", "karla@email.com"),
                    new Users("user2", "user2@email.com"),
                    new Users("user3", "user3@email.com")
                );

                for (Users user : usersList) {
                    log.info("Initial load: {}", usersRepository.save(user));
                }
            }
        };
    }

}
