package com.karla.apibooks;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BooksControllerTests {

    @Autowired
    TestRestTemplate restTemplate;

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

}
