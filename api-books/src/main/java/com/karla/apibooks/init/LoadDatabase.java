package com.karla.apibooks.init;

import com.karla.apibooks.model.Books;
import com.karla.apibooks.repository.BooksRepository;
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
    CommandLineRunner initDatabase(BooksRepository booksRepository) {
        return args -> {
            if (booksRepository.count() == 0) {
                List<Books> booksList = Arrays.asList(
                    new Books("Harry Potter and the Philosopher's Stone", "J.K. Rowling"),
                    new Books("Harry Potter and the Chamber of Secrets", "J.K. Rowling"),
                    new Books("Harry Potter and the Prisoner of Azkaban", "J.K. Rowling"),
                    new Books("Harry Potter and the Goblet of Fire", "J.K. Rowling"),
                    new Books("Harry Potter and the Order of the Phoenix", "J.K. Rowling"),
                    new Books("Harry Potter and the Half-Blood Prince", "J.K. Rowling"),
                    new Books("Harry Potter and the Deathly Hallows", "J.K. Rowling"),
                    new Books("The Hobbit", "J.R.R. Tolkien"),
                    new Books("1984", "George Orwell"),
                    new Books("To Kill a Mockingbird", "Harper Lee"),
                    new Books("The Great Gatsby", "F. Scott Fitzgerald"),
                    new Books("Pride and Prejudice", "Jane Austen")
                );

                for (Books books : booksList) {
                    log.info("Initial load: {}", booksRepository.save(books));
                }
            }
        };
    }

}
