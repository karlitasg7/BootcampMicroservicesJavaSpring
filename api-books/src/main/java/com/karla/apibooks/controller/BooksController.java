package com.karla.apibooks.controller;

import com.karla.apibooks.dto.BookDTO;
import com.karla.apibooks.dto.UserDTO;
import com.karla.apibooks.exceptions.BookNotAvailableException;
import com.karla.apibooks.exceptions.UserNotExistsException;
import com.karla.apibooks.model.Books;
import com.karla.apibooks.repository.BooksRepository;
import com.karla.apibooks.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BooksController {

    private final BooksRepository booksRepository;
    private final UsersService    usersService;

    public BooksController(BooksRepository booksRepository, UsersService usersService) {
        this.booksRepository = booksRepository;
        this.usersService    = usersService;
    }

    @Operation(summary = "Get books", description = "Get the list of all books")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200", description = "Response with books list")
        }
    )
    @GetMapping
    public ResponseEntity<List<BookDTO>> getAll() {
        List<Books> books = booksRepository.findAll();

        List<BookDTO> booksDTO = new ArrayList<>();

        for (Books book : books) {
            BookDTO bookDTO = new BookDTO(book.getId(),
                                          book.getTitle(),
                                          book.getAuthor(),
                                          book.isAvailable(),
                                          book.getUserId());

            if (!book.isAvailable()) {
                UserDTO userInfo = usersService.getById(book.getUserId());

                if (userInfo != null) {
                    bookDTO.setUserName(userInfo.getName());
                    bookDTO.setUserEmail(userInfo.getEmail());
                }
            }

            booksDTO.add(bookDTO);
        }

        return ResponseEntity.ok(booksDTO);
    }

    @Operation(summary = "Return Book", description = "Return book by Id")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "204", description = "Response empty")
        }
    )
    @PutMapping("/{id}/return")
    public ResponseEntity<Void> returnBook(@PathVariable Long id) {
        Books book = booksRepository.findById(id).orElse(null);

        if (book == null || book.isAvailable()) {
            return ResponseEntity.notFound().build();
        }

        book.setAvailable(true);
        book.setUserId(null);
        booksRepository.save(book);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Borrow Book", description = "Borrow book by Id")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "204", description = "Response empty")
        }
    )
    @PutMapping("/{id}/borrow")
    public ResponseEntity<Void> borrowBook(@PathVariable Long id, @RequestParam Long userId) {
        Books book = booksRepository.findById(id).orElse(null);

        if (book == null || !book.isAvailable()) {
            throw new BookNotAvailableException();
        }

        // Check if user exists
        UserDTO userDTO = getUserDTO(userId);
        if (userDTO == null) {
            throw new UserNotExistsException();
        }

        book.setAvailable(false);
        book.setUserId(userId);

        booksRepository.save(book);

        return ResponseEntity.noContent().build();
    }

    private UserDTO getUserDTO(Long userId) {
        try {
            return usersService.getById(userId);
        } catch (Exception e) {
            return null;
        }
    }

}
