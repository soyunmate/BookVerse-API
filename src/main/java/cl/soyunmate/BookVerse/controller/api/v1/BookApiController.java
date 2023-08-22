package cl.soyunmate.BookVerse.controller.api.v1;

import cl.soyunmate.BookVerse.DTO.*;
import cl.soyunmate.BookVerse.DTO.mapper.BookMapper;
import cl.soyunmate.BookVerse.DTO.mapper.ResponseMapper;
import cl.soyunmate.BookVerse.model.*;
import cl.soyunmate.BookVerse.model.enums.ETag;
import cl.soyunmate.BookVerse.service.IAuthorService;
import cl.soyunmate.BookVerse.service.IBookService;
import cl.soyunmate.BookVerse.service.IGenreService;
import cl.soyunmate.BookVerse.service.ITagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class BookApiController {
    @Autowired
    private IBookService bookService;
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private ResponseMapper responseMapper;

    @Operation(summary = "Find books with optional filters")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved books", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/books")
    public ResponseEntity<Response> findAll(@Parameter(description = "Filter by author's last name") @RequestParam(required = false, defaultValue = "") String author,
                                            @Parameter(description = "Filter by genre name") @RequestParam(required = false, defaultValue = "") String genre,
                                            @Parameter(description = "Filter by publisher name") @RequestParam(required = false, defaultValue = "") String publisher,
                                            @Parameter(description = "Filter by tag name") @RequestParam(required = false, defaultValue = "") String tag,
                                            HttpServletRequest request) {
        List<Book> bookList = bookService.findAll();
        boolean passedAnyFilter = false;

        if(!author.isBlank()) {
            bookList = bookList.stream().filter(b -> b.getAuthor().getLastName().toLowerCase().trim().equals(author)).toList();
            passedAnyFilter = true;
        }

        if(!genre.isBlank()) {
            bookList = bookList.stream().filter(b-> b.getGenre().stream().anyMatch(g -> g.getName().toLowerCase().trim().equals(genre) )).toList();
            passedAnyFilter = true;
        }

        if (!publisher.isBlank()) {
            bookList = bookList.stream().filter(b -> b.getPublisher().getName().toLowerCase().trim().equals(publisher)).toList();
            passedAnyFilter = true;
        }

        if (!tag.isBlank()) {
            bookList = bookList.stream().filter( b -> b.getTags().stream().anyMatch(tg -> tg.getName().name().toLowerCase().trim().equals(tag))).toList();
            passedAnyFilter = true;
        }

        if (!passedAnyFilter && !request.getParameterMap().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(responseMapper.toResponse(null,
                            "Invalid Query parameter",
                            "Book(s)",
                            HttpStatus.BAD_REQUEST));
        }

        List<BookDTO> bookDTOList = bookList.stream().map(book -> bookMapper.toDto(book)).toList();

        return  ResponseEntity.ok(
                responseMapper.toResponse(
                        bookDTOList,
                        passedAnyFilter ? "Filtered list retrieved":"All Books Retrieved",
                        "Book(s)",
                        HttpStatus.OK));
    }

    @Operation(summary = "Find book by ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved book", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "404", description = "Book not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @GetMapping("/books/{id}")
    public ResponseEntity<Response> findByid(@PathVariable(required = false) Long id) {

        Optional<Book> optionalBook = bookService.findById(id);

        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            BookDTO bookDTO = bookMapper.toDto(book);

            return  ResponseEntity.ok(
                    responseMapper.toResponse(
                            bookDTO,
                            "Book Retrieved",
                            "Book",
                            HttpStatus.OK));

        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(responseMapper.toResponse(null,
                        "Book not found",
                        "Book",
                        HttpStatus.NOT_FOUND));

    }


    @Operation(summary = "Find books by publisher ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved publisher's books", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @GetMapping("/publishers/{id}/books")
    public ResponseEntity<Response> findPublisherBooks(@PathVariable Long id) {
        Set<Book> bookList = bookService.findByPublisher(Publisher.builder().id(id).build());
        List<BookDTO> bookDTOList = bookList.stream().map(book -> bookMapper.toDto(book)).toList();

        return  ResponseEntity.ok(
                responseMapper.toResponse(
                        bookDTOList,
                        bookDTOList.isEmpty() ? "Publisher does not have any books" : "Publisher´s books retrieved",
                        "Book(s)",
                        HttpStatus.OK));

    }
    @Operation(summary = "Find books by author ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved author's books", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @GetMapping("/authors/{id}/books")
    public ResponseEntity<Response> findAuthorBooks(@PathVariable Long id) {
        Set<Book> bookList = bookService.findByAuthor(Author.builder().id(id).build());
        List<BookDTO> bookDTOList = bookList.stream().map(book -> bookMapper.toDto(book)).toList();

        return  ResponseEntity.ok(
                responseMapper.toResponse(
                        bookDTOList,
                        bookDTOList.isEmpty() ? "Author does not have any books" : "Author´s books retrieved",
                        "Book(s)",
                        HttpStatus.OK));

    }

    @Operation(summary = "Find books by genre ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved genre's books", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @GetMapping("/genres/{id}/books")
    public ResponseEntity<Response> findGenreBooks(@PathVariable Long id) {
        Set<Book> bookList = bookService.findByGenre(Genre.builder().id(id).build());
        List<BookDTO> bookDTOList = bookList.stream().map(book -> bookMapper.toDto(book)).toList();

        return  ResponseEntity.ok(
                responseMapper.toResponse(
                        bookDTOList,
                        bookDTOList.isEmpty() ? "Genre does not have any books" : "Genre´s books retrieved",
                        "Book(s)",
                        HttpStatus.OK));

    }



    @Operation(summary = "Create a new book")
    @ApiResponse(responseCode = "201", description = "Book Created", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "400", description = "Missing one or more required fields", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @PostMapping("/books")
    public ResponseEntity<Response> save(@Valid @RequestBody BookDTO booKDTO) {
        try {
            Book bookToSave = bookMapper.toEntity(booKDTO);
            bookService.save(bookToSave);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(responseMapper.toResponse(null,
                            "Book Entry Added",
                            "Book",
                            HttpStatus.CREATED));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(responseMapper.toResponse(null,
                            "Missing a required parameter",
                            "Book(s)",
                            HttpStatus.BAD_REQUEST));

        }

    }

    @Operation(summary = "Update book by ID")
    @ApiResponse(responseCode = "200", description = "Book Updated", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "404", description = "Book not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @PutMapping("/books/{id}")
    public ResponseEntity<Response> updateById(@PathVariable Long id, @Valid @RequestBody BookDTO booKDTO) {
        Optional<Book> optionalBook = bookService.findById(id);

            if (optionalBook.isPresent()) {
                Book updatedBook = optionalBook.get();
                Book mappedBook = bookMapper.toEntity(booKDTO);
                mappedBook.setId(updatedBook.getId());
                bookService.save(mappedBook);

                return  ResponseEntity.ok(
                        responseMapper.toResponse(
                                null,
                                "Book Updated",
                                "Book",
                                HttpStatus.OK));

            }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(responseMapper.toResponse(null,
                        "Book not found",
                        "Book",
                        HttpStatus.NOT_FOUND));
    }


    @Operation(summary = "Delete book by ID")
    @ApiResponse(responseCode = "202", description = "Book Eliminated", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "404", description = "Book to delete was not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @DeleteMapping("/books/{id}")
    public ResponseEntity<Response> deleteByid(@PathVariable Long id) {
        Optional<Book> optionalBook = bookService.findById(id);

        if (optionalBook.isPresent()) {
            bookService.deleteById(optionalBook.get().getId());
            return  ResponseEntity.ok(
                    responseMapper.toResponse(
                            null,
                            "Book Deleted",
                            "Book",
                            HttpStatus.OK));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(responseMapper.toResponse(null,
                        "Book not found",
                        "Book",
                        HttpStatus.NOT_FOUND));
    }


}
