package com.karla.apiusers;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.karla.apiusers.model.Users;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsersControllerTests {

    @Autowired
    TestRestTemplate restTemplate;

    /**
     * Verify if the service returns a list of users
     */
    @Test
    void shouldReturnUserList() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/users", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        int             totalUsers      = documentContext.read("$.length()");
        assertThat(totalUsers).isEqualTo(3);

        JSONArray ids = documentContext.read("$..id");
        assertThat(ids).containsExactlyInAnyOrder(1, 2, 3);

        JSONArray names = documentContext.read("$..name");
        assertThat(names).containsExactlyInAnyOrder("karla", "user2", "user3");
    }

    /**
     * Verify if the service returns a user by ID
     */
    @Test
    void shouldReturnUserById() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/users/1", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        Number          id              = documentContext.read("$.id");
        assertThat(id).isEqualTo(1);

        String name = documentContext.read("$.name");
        assertThat(name).isEqualTo("karla");

        String email = documentContext.read("$.email");
        assertThat(email).isEqualTo("karla@email.com");
    }

    /**
     * Verify if the service returns a 404 error when trying to get a user with an unknown ID
     */
    @Test
    void shouldReturnNotFoundWithAnUnknownId() {
        ResponseEntity<String> response = restTemplate
            .getForEntity("/api/users/9999", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isBlank();
    }

    /**
     * Verify if the service creates a new user
     */
    @Test
    @DirtiesContext
    void shouldCreateANewUser() {
        Users userInfo = new Users("Test", "test@test.com");
        ResponseEntity<Void> response = restTemplate
            .postForEntity("/api/users", userInfo, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        URI location = response.getHeaders().getLocation();
        ResponseEntity<String> getResponse = restTemplate
            .getForEntity(location, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
        Number          id              = documentContext.read("$.id");
        String          name            = documentContext.read("$.name");
        String          email           = documentContext.read("$.email");

        assertThat(id).isNotNull();
        assertThat(name).isEqualTo("Test");
        assertThat(email).isEqualTo("test@test.com");
    }

    /**
     * Verify if the service returns a 409 error when trying to create a user with an existing email
     */
    @Test
    void shouldReturnUserExistsWhenCreateUser() {
        Users userInfo = new Users("karla", "karla@email.com");
        ResponseEntity<Void> response = restTemplate
            .postForEntity("/api/users", userInfo, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    /**
     * Verify if the service update an existing user
     */
    @Test
    @DirtiesContext
    void shouldUpdateAnExistingUser() {
        Users             user    = new Users("New Name", "newmail@test.com");
        HttpEntity<Users> request = new HttpEntity<>(user);

        ResponseEntity<Void> response = restTemplate
            .exchange("/api/users/1", HttpMethod.PUT, request, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> getResponse = restTemplate
            .getForEntity("/api/users/1", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
        Number          id              = documentContext.read("$.id");
        String          name            = documentContext.read("$.name");
        String          email           = documentContext.read("$.email");

        assertThat(id).isEqualTo(1);
        assertThat(name).isEqualTo("New Name");
        assertThat(email).isEqualTo("newmail@test.com");
    }

    /**
     * Verify if the service returns a 409 error when trying to update a user with an existing email
     */
    @Test
    void shouldReturnUserExistsWhenUpdateUser() {
        Users             userInfo = new Users("karla", "karla@email.com");
        HttpEntity<Users> request  = new HttpEntity<>(userInfo);

        ResponseEntity<Void> response = restTemplate
            .exchange("/api/users/2", HttpMethod.PUT, request, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    /**
     * Verify if the service delete an existing user
     */
    @Test
    @DirtiesContext
    void shouldDeleteAnExistingUserById() {
        ResponseEntity<Void> response = restTemplate
            .exchange("/api/users/3", HttpMethod.DELETE, null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> getResponse = restTemplate
            .getForEntity("/api/users/3", String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /**
     * Verify if the service returns a 404 error when trying to delete a user with an unknown ID
     */
    @Test
    void shouldReturnNotFoundWhenDeleteUnknownId() {
        ResponseEntity<Void> response = restTemplate
            .exchange("/api/users/3000", HttpMethod.DELETE, null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

}
