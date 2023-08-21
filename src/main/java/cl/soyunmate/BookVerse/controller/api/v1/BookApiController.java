package cl.soyunmate.BookVerse.controller.api.v1;

import cl.soyunmate.BookVerse.DTO.*;
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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class BookApiController {
    @Autowired
    private IBookService bookService;

    @Autowired
    private IGenreService genreService;

    @Autowired
    private ITagService tagService;

    @Operation(summary = "Find books with optional filters")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved books", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
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
                    .body(Response.builder()
                            .timeStamp(LocalDateTime.now())
                            .message("Invalid Query parameter")
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }


        List<BookDTO> bookDTOList = bookList.stream()
                .map(book -> BookDTO.builder()
                        .id(book.getId())
                        .isbn(book.getIsbn())
                        .title(book.getTitle())
                        .author(AuthorDTO.builder()
                                .id(book.getAuthor().getId())
                                .firstName(book.getAuthor().getFirstName())
                                .lastName(book.getAuthor().getLastName())
                                .build())
                        .genre(book.getGenre().stream().map(Genre::getName)
                                .collect(Collectors.toSet()))
                        .description(book.getDescription())
                        .publishDate(book.getPublishDate())
                        .publisher(PublisherDTO.builder().name(book.getPublisher().getName()).build())
                        .language(book.getLanguage())
                        .pages(book.getPages())
                        .tags(book.getTags().stream().map(tg -> tg.getName().name())
                                .collect(Collectors.toSet()))
                        .stock(book.getStock())
                        .build() )
                .toList();

            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(LocalDateTime.now())
                            .data(Map.of("books", bookDTOList))
                            .message( passedAnyFilter ? "Filtered list retrieved":"All Books Retrieved")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                    .build());
    }

    @Operation(summary = "Find book by ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved book", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "404", description = "Book not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @GetMapping("/books/{id}")
    public ResponseEntity<Response> findByid(@PathVariable(required = false) Long id) {

        Optional<Book> optionalBook = bookService.findById(id);

        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            BookDTO booKDTO = BookDTO.builder()
                    .id(book.getId())
                    .isbn(book.getIsbn())
                    .title(book.getTitle())
                    .author(AuthorDTO.builder()
                            .id(book.getAuthor().getId())
                            .firstName(book.getAuthor().getFirstName())
                            .lastName(book.getAuthor().getLastName())
                            .build())
                    .genre(book.getGenre().stream().map(Genre::getName)
                            .collect(Collectors.toSet()))
                    .description(book.getDescription())
                    .publishDate(book.getPublishDate())
                    .publisher(PublisherDTO.builder().name(book.getPublisher().getName()).build())
                    .language(book.getLanguage())
                    .pages(book.getPages())
                    .tags(book.getTags().stream().map(tg -> tg.getName().name())
                            .collect(Collectors.toSet()))
                    .stock(book.getStock())
                    .build();

            return ResponseEntity.ok(Response.builder()
                    .timeStamp(LocalDateTime.now())
                    .data(Map.of("book", booKDTO))
                    .message("Book Retrieved")
                    .status(HttpStatus.OK)
                    .statusCode(HttpStatus.OK.value())
                    .build());

        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Book Not Found")
                        .status(HttpStatus.NOT_FOUND)
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .build());

    }
    @Operation(summary = "Create a new book")
    @ApiResponse(responseCode = "201", description = "Book Created", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "400", description = "Missing one or more required fields", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @PostMapping("/books")
    public ResponseEntity<Response> save(@Valid @RequestBody BookDTO booKDTO) {
        try {
            Book bookToSave = Book.builder()
                    .isbn(booKDTO.getIsbn())
                    .title(booKDTO.getTitle())
                    .author(Author.builder().id(booKDTO.getAuthor().getId()).build())
                    .genre(booKDTO.getGenre().stream().map(genre -> Genre.builder().name(genre.toUpperCase()).build()).collect(Collectors.toSet()))
                    .description(booKDTO.getDescription())
                    .publishDate(booKDTO.getPublishDate())
                    .publisher(Publisher.builder().id(booKDTO.getPublisher().getId()).build())
                    .language(booKDTO.getLanguage())
                    .pages(booKDTO.getPages())
                    .tags(booKDTO.getTags().stream().map(tag -> Tag.builder().name(ETag.valueOf(tag)).build()).collect(Collectors.toSet()))
                    .stock(booKDTO.getStock())
                    .build();

            bookToSave.getGenre().stream().map( g -> {
                if (genreService.findByName(g.getName()).isEmpty()) {
                    genreService.save(g);
                }
                return null;
            });

            bookToSave.getTags().stream().map( tg -> {
                if (tagService.findByName(tg.getName()).isEmpty()) {
                    tagService.save(tg);
                }
                return null;
            });


            bookService.save(bookToSave);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Response.builder()
                            .timeStamp(LocalDateTime.now())
                            .message("Book Created")
                            .status(HttpStatus.CREATED)
                            .statusCode(HttpStatus.CREATED.value())
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Response.builder()
                            .timeStamp(LocalDateTime.now())
                            .message("Missing one or more required fields" + e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());

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
                updatedBook.setTitle(booKDTO.getTitle());
                updatedBook.setAuthor(Author.builder().id(booKDTO.getAuthor().getId()).build());
                updatedBook.setDescription(booKDTO.getDescription());
                updatedBook.setIsbn(booKDTO.getIsbn());
                updatedBook.setGenre(booKDTO.getGenre().stream().map(genre -> Genre.builder().name(genre).build()).collect(Collectors.toSet()));
                updatedBook.setLanguage(booKDTO.getLanguage());
                updatedBook.setPublishDate(booKDTO.getPublishDate());
                updatedBook.setPublisher(Publisher.builder().id(booKDTO.getPublisher().getId()).build());
                updatedBook.setStock(booKDTO.getStock());
                updatedBook.setPages(booKDTO.getPages());
                updatedBook.setTags(booKDTO.getTags().stream().map(tag -> Tag.builder().name(ETag.valueOf(tag)).build()).collect(Collectors.toSet()));

                bookService.save(updatedBook);

                return ResponseEntity.status(HttpStatus.OK).body(Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Book Updated")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());

            }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Book Not Found")
                        .status(HttpStatus.NOT_FOUND)
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .build());
    }



    @Operation(summary = "Delete book by ID")
    @ApiResponse(responseCode = "202", description = "Book Eliminated", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "404", description = "Book to delete was not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @DeleteMapping("/books/{id}")
    public ResponseEntity<Response> deleteByid(@PathVariable Long id) {
        Optional<Book> optionalBook = bookService.findById(id);

        if (optionalBook.isPresent()) {
            bookService.deleteById(optionalBook.get().getId());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(Response.builder()
                    .timeStamp(LocalDateTime.now())
                    .message("Book Eliminated")
                    .status(HttpStatus.ACCEPTED)
                    .statusCode(HttpStatus.ACCEPTED.value())
                    .build());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Book to delete was not found")
                        .status(HttpStatus.NOT_FOUND)
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .build());
    }


}
