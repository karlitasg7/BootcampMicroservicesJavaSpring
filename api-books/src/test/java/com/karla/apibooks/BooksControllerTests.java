package com.karla.apibooks;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.karla.apibooks.dto.UserDTO;
import com.karla.apibooks.service.UsersService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BooksControllerTests {

    @Autowired
    TestRestTemplate restTemplate;

    @MockitoBean
    UsersService usersService;

    /**
     * Verify if the service returns a list of books
     */
    @Test
    void shouldReturnBooksList() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/books", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        int             totalUsers      = documentContext.read("$.length()");
        assertThat(totalUsers).isEqualTo(12);
    }

    /**
     * Verify if the service returns a 404 error when trying to borrow a book with an unknown ID
     */
    @Test
    void shouldReturnNotFoundWithAnUnknownId() {
        ResponseEntity<String> response = restTemplate
            .getForEntity("/api/books/borrow/9999", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /**
     * Verify if the service returns a 404 error when trying to return a book with an unknown ID
     */
    @Test
    void shouldReturnNotFoundWithAnUnknownIdWhenTryToReturn() {
        ResponseEntity<String> response = restTemplate
            .getForEntity("/api/books/return/9999", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /**
     * Verify if the service can borrow a book
     */
    @Test
    void shouldBorrowABook() {
        // Mock the user service to return a user
        UserDTO userDTO = new UserDTO();
        userDTO.setName("User");
        userDTO.setEmail("user@example.com");

        Mockito.when(usersService.getById(1L)).thenReturn(userDTO);

        ResponseEntity<Void> response = restTemplate
            .exchange("/api/books/1/borrow?userId=1", HttpMethod.PUT, new HttpEntity<>(""), Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    /**
     * Verify if the service can return a borrow book
     */
    @Test
    void shouldReturnABook() {
        // Mock the user service to return a user
        UserDTO userDTO = new UserDTO();
        userDTO.setName("User");
        userDTO.setEmail("user@example.com");

        Mockito.when(usersService.getById(1L)).thenReturn(userDTO);

        // First borrow the book
        ResponseEntity<Void> response = restTemplate
            .exchange("/api/books/1/borrow?userId=1", HttpMethod.PUT, new HttpEntity<>(""), Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        // Now return the book
        ResponseEntity<Void> responseReturn = restTemplate
            .exchange("/api/books/1/return", HttpMethod.PUT, new HttpEntity<>(""), Void.class);

        assertThat(responseReturn.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }

}
