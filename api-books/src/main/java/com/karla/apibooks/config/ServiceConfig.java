package com.karla.apibooks.config;

import com.karla.apibooks.service.UsersService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class ServiceConfig {

    @Bean
    public UsersService miApiClient() {

        RestClient restClient = RestClient.builder()
            .baseUrl("http://localhost:8080")
            .build();

        HttpServiceProxyFactory factory = HttpServiceProxyFactory
            .builderFor(RestClientAdapter.create(restClient))
            .build();

        return factory.createClient(UsersService.class);
    }
}
