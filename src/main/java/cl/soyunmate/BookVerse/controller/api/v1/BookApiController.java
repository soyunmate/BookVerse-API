package cl.soyunmate.BookVerse.controller.api.v1;

import cl.soyunmate.BookVerse.DTO.*;
import cl.soyunmate.BookVerse.model.*;
import cl.soyunmate.BookVerse.model.enums.EGenre;
import cl.soyunmate.BookVerse.model.enums.ETag;
import cl.soyunmate.BookVerse.service.IBookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/books")
    public ResponseEntity<Response> findAll() {
        List<Book> bookList = bookService.findAll();
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
                        .genre(book.getGenre().stream().map(genre -> GenreDTO.builder()
                                        .name(EGenre.valueOf(String.valueOf(genre.getName())))
                                        .build())
                                .collect(Collectors.toSet()))
                        .description(book.getDescription())
                        .publishDate(book.getPublishDate())
                        .publisher(PublisherDTO.builder().name(book.getPublisher().getName()).build())
                        .language(book.getLanguage())
                        .pages(book.getPages())
                        .tags(book.getTags().stream().map(tag -> TagDTO.builder()
                                .name(ETag.valueOf(String.valueOf(tag.getName()))).build())
                                .collect(Collectors.toSet()))
                        .stock(book.getStock())
                        .build() )
                .toList();

            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(LocalDateTime.now())
                            .data(Map.of("books", bookDTOList))
                            .message("All Books Retrieved")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                    .build());
    }

    @Operation(summary = "Get a book by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the book",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content) })
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
                    .genre(book.getGenre().stream().map(genre -> GenreDTO.builder()
                                    .name(EGenre.valueOf(String.valueOf(genre.getName())))
                                    .build())
                            .collect(Collectors.toSet()))
                    .description(book.getDescription())
                    .publishDate(book.getPublishDate())
                    .publisher(PublisherDTO.builder().name(book.getPublisher().getName()).build())
                    .language(book.getLanguage())
                    .pages(book.getPages())
                    .tags(book.getTags().stream().map(tag -> TagDTO.builder()
                                    .name(ETag.valueOf(String.valueOf(tag.getName()))).build())
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
    @PostMapping("/books")
    public ResponseEntity<Response> save(@RequestBody BookDTO booKDTO) {
        try {


        Book bookToSave = Book.builder()
                .id(booKDTO.getId())
                .isbn(booKDTO.getIsbn())
                .title(booKDTO.getTitle())
                .author(Author.builder().id(booKDTO.getAuthor().getId()).build())
                .genre(booKDTO.getGenre().stream().map(genre -> Genre.builder().id(genre.getId()).build()).collect(Collectors.toSet()))
                .description(booKDTO.getDescription())
                .publishDate(booKDTO.getPublishDate())
                .publisher(Publisher.builder().id(booKDTO.getPublisher().getId()).build())
                .language(booKDTO.getLanguage())
                .pages(booKDTO.getPages())
                .tags(booKDTO.getTags().stream().map(tag -> Tag.builder().id(tag.getId()).build()).collect(Collectors.toSet()))
                .stock(booKDTO.getStock())
                .build();

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
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());

        }

    }
    @PutMapping("/books/{id}")
    public ResponseEntity<Response> updateById(@PathVariable Long id, @RequestBody BookDTO booKDTO) {
        Optional<Book> optionalBook = bookService.findById(id);

        try {
            if (optionalBook.isPresent()) {
                Book updatedBook = optionalBook.get();
                updatedBook.setTitle(booKDTO.getTitle());
                updatedBook.setAuthor(Author.builder().id(booKDTO.getAuthor().getId()).build());
                updatedBook.setDescription(booKDTO.getDescription());
                updatedBook.setIsbn(booKDTO.getIsbn());
                updatedBook.setGenre(booKDTO.getGenre().stream().map(genre -> Genre.builder().id(genre.getId()).build()).collect(Collectors.toSet()));
                updatedBook.setLanguage(booKDTO.getLanguage());
                updatedBook.setPublishDate(booKDTO.getPublishDate());
                updatedBook.setPublisher(Publisher.builder().id(booKDTO.getPublisher().getId()).build());
                updatedBook.setStock(booKDTO.getStock());
                updatedBook.setPages(booKDTO.getPages());
                updatedBook.setTags(booKDTO.getTags().stream().map(tag -> Tag.builder().id(tag.getId()).build()).collect(Collectors.toSet()));

                bookService.save(updatedBook);

                return ResponseEntity.status(HttpStatus.OK).body(Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Book Updated")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());

            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Response.builder()
                            .timeStamp(LocalDateTime.now())
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }

        return ResponseEntity.badRequest().build();
    }



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
