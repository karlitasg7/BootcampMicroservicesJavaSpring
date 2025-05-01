package com.karla.apibooks.config;

import com.karla.apibooks.service.UsersService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.Optional;

@Configuration
public class ServiceConfig {

    @Bean
    public UsersService usersService() {

        String usersApiUrl = Optional.ofNullable(System.getenv("USERS_API_URL"))
            .filter(s -> !s.isBlank())
            .orElse("http://localhost:8080");

        RestClient restClient = RestClient.builder()
            .baseUrl(usersApiUrl)
            .build();

        HttpServiceProxyFactory factory = HttpServiceProxyFactory
            .builderFor(RestClientAdapter.create(restClient))
            .build();

        return factory.createClient(UsersService.class);
    }
}
