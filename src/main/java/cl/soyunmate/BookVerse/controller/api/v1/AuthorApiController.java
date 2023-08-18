package cl.soyunmate.BookVerse.controller.api.v1;

import cl.soyunmate.BookVerse.DTO.*;
import cl.soyunmate.BookVerse.model.Author;
import cl.soyunmate.BookVerse.model.Book;
import cl.soyunmate.BookVerse.model.Response;
import cl.soyunmate.BookVerse.model.enums.ETag;
import cl.soyunmate.BookVerse.service.IAuthorService;
import cl.soyunmate.BookVerse.service.IBookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class AuthorApiController {
    @Autowired
    private IAuthorService authorService;

    @Autowired
    private IBookService bookService;

    @Operation(summary = "Find authors with filters")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved authors", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "400", description = "Invalid Query parameter", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @GetMapping("/authors")
    public ResponseEntity<Response> findAll(@Parameter(description = "Filter by author's last name") @RequestParam(required = false, defaultValue = "") String author,
                                            HttpServletRequest request) {

        List<Author> authorList = authorService.findAll();
        boolean passedAnyFilter = false;

        if (!author.isBlank()) {
            authorList = authorList.stream().filter(au -> au.getLastName().toLowerCase().equals(author)).toList();
        }

        if (!passedAnyFilter && !request.getParameterMap().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Response.builder()
                            .timeStamp(LocalDateTime.now())
                            .message("Invalid Query parameter")
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }

        List<AuthorDTO> authorDTOList = authorList.stream().map( au -> AuthorDTO
                        .builder()
                        .id(au.getId())
                        .firstName(au.getFirstName())
                        .lastName(au.getLastName())
                        .nationality(au.getNationality())
                        .birthDate(au.getBirthDate())
                        .biography(au.getBiography())
                .build())
                .toList();


        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("books", authorDTOList))
                        .message("All Books Authors Retrieved")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    @Operation(summary = "Find author by ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved author", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "404", description = "Author not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @GetMapping("/authors/{id}")
    public ResponseEntity<Response> findById(@Parameter(description = "ID of the author to be obtained") @PathVariable Long id) {
        Optional<Author> optionalAuthor = authorService.findById(id);

        if (optionalAuthor.isPresent()) {
            Author author = optionalAuthor.get();
            AuthorDTO authorDTO = AuthorDTO.builder()
                    .id(author.getId())
                    .firstName(author.getFirstName())
                    .lastName(author.getLastName())
                    .nationality(author.getNationality())
                    .birthDate(author.getBirthDate())
                    .biography(author.getBiography())
                    .build();

            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(LocalDateTime.now())
                            .data(Map.of("Author", authorDTO))
                            .message("Author Retrieved")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build());

        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Author Not Found")
                        .status(HttpStatus.NOT_FOUND)
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .build());
    }

    @Operation(summary = "Find books by author ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved author's books", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "404", description = "Author not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @GetMapping("/authors/{id}/books")
    public ResponseEntity<Response> findAuthorBooks(@Parameter(description = "ID of the author to get books from") @PathVariable Long id) {
        Optional<Author> optionalAuthor = authorService.findById(id);

        if (optionalAuthor.isPresent()) {
            Set<Book> authorBooks = bookService.findByAuthor(optionalAuthor.get());
            List<BookDTO> bookDTOList = authorBooks.stream().map(
                    book -> BookDTO.builder()
                            .id(book.getId())
                            .isbn(book.getIsbn())
                            .title(book.getTitle())
                            .author(AuthorDTO.builder()
                                    .id(book.getAuthor().getId())
                                    .firstName(book.getAuthor().getFirstName())
                                    .lastName(book.getAuthor().getLastName())
                                    .build())
                            .genre(book.getGenre().stream().map(g -> GenreDTO.builder()
                                            .name(g.getName())
                                            .build())
                                    .collect(Collectors.toSet()))
                            .description(book.getDescription())
                            .publishDate(book.getPublishDate())
                            .publisher(PublisherDTO.builder().name(book.getPublisher().getName()).build())
                            .language(book.getLanguage())
                            .pages(book.getPages())
                            .tags(book.getTags().stream().map(tg -> TagDTO.builder()
                                            .name(ETag.valueOf(java.lang.String.valueOf(tg.getName()))).build())
                                    .collect(Collectors.toSet()))
                            .stock(book.getStock())
                            .build()
                    ).toList();

            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(LocalDateTime.now())
                            .data(Map.of("books", bookDTOList))
                            .message( "Author´s books retrieved")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Author Not Found")
                        .status(HttpStatus.NOT_FOUND)
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .build());
    }

    @Operation(summary = "Create a new author")
    @ApiResponse(responseCode = "201", description = "Author Created", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "400", description = "Missing one or more required fields", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @PostMapping("/authors")
    public ResponseEntity<Response> save( @Valid @RequestBody AuthorDTO authorDTO) {

        try {
            Author authorToSave = Author.builder()
                    .firstName(authorDTO.getFirstName())
                    .lastName(authorDTO.getLastName())
                    .nationality(authorDTO.getNationality())
                    .birthDate(authorDTO.getBirthDate())
                    .biography(authorDTO.getBiography())
                    .build();

            authorService.save(authorToSave);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Response.builder()
                            .timeStamp(LocalDateTime.now())
                            .message("Author Created")
                            .status(HttpStatus.CREATED)
                            .statusCode(HttpStatus.CREATED.value())
                            .build());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Response.builder()
                            .timeStamp(LocalDateTime.now())
                            .message("Missing one or more required fields")
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());

        }

    }

    @Operation(summary = "Update author by ID")
    @ApiResponse(responseCode = "200", description = "Author Updated", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "404", description = "Author not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @PutMapping("/authors/{id}")
    public ResponseEntity<Response> updateById(@Parameter(description = "ID of the author to be updated") @PathVariable Long id,
                                               @Valid @RequestBody AuthorDTO authorDTO) {
        Optional<Author> optionalAuthor = authorService.findById(id);

        if (optionalAuthor.isPresent()) {
            Author updatedAuthor = optionalAuthor.get();
            updatedAuthor.setFirstName(authorDTO.getFirstName());
            updatedAuthor.setLastName(authorDTO.getLastName());
            updatedAuthor.setNationality(authorDTO.getNationality());
            updatedAuthor.setBiography(authorDTO.getBiography());
            updatedAuthor.setNationality(authorDTO.getNationality());
            authorService.save(updatedAuthor);

            return ResponseEntity.status(HttpStatus.OK).body(Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Author Updated")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());

        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Author Not Found")
                        .status(HttpStatus.NOT_FOUND)
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .build());
    }

    @Operation(summary = "Delete author by ID")
    @ApiResponse(responseCode = "200", description = "Author Eliminated", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "404", description = "Author not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @DeleteMapping("/authors/{id}")
    public ResponseEntity<Response> deleteById(@Parameter(description = "ID of the author to be deleted") @PathVariable Long id) {

        Optional<Author> optionalAuthor = authorService.findById(id);

        if (optionalAuthor.isPresent()) {
            authorService.deleteById(optionalAuthor.get().getId());
            return ResponseEntity.status(HttpStatus.OK).body(Response.builder()
                    .timeStamp(LocalDateTime.now())
                    .message("Author Eliminated")
                    .status(HttpStatus.OK)
                    .statusCode(HttpStatus.OK.value())
                    .build());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Author Not Found")
                        .status(HttpStatus.NOT_FOUND)
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .build());
    }

}
