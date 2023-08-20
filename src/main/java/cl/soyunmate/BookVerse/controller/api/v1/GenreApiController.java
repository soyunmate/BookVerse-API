package cl.soyunmate.BookVerse.controller.api.v1;

import cl.soyunmate.BookVerse.DTO.*;
import cl.soyunmate.BookVerse.model.Book;
import cl.soyunmate.BookVerse.model.Genre;
import cl.soyunmate.BookVerse.model.Publisher;
import cl.soyunmate.BookVerse.model.Response;
import cl.soyunmate.BookVerse.model.enums.ETag;
import cl.soyunmate.BookVerse.service.IBookService;
import cl.soyunmate.BookVerse.service.IGenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
public class GenreApiController {
    @Autowired
    private IGenreService genreService;

    @Autowired
    private IBookService bookService;

    @GetMapping("/genres")
    public ResponseEntity<Response> findAll() {
        List<Genre> genreList = genreService.findAll();
        List<GenreDTO> genreDTOList = genreList.stream()
                .map(genre -> GenreDTO.builder()
                .id(genre.getId())
                .name(genre.getName())
                .description(genre.getDescription())
                .icon(genre.getIcon())
                .build())
                .toList();

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("Genres", genreDTOList))
                        .message("All Genres Retrieved")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());

    }

    @GetMapping("/genres/{id}")
    public ResponseEntity<Response> findByid(@PathVariable Long id) {

        Optional<Genre> optionalGenre = genreService.findById(id);

        if (optionalGenre.isPresent()) {
            Genre genre = optionalGenre.get();
            GenreDTO genreDTO = GenreDTO.builder()
                    .id(genre.getId())
                    .name(genre.getName())
                    .description(genre.getDescription())
                    .icon(genre.getIcon())
                    .build();

            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(LocalDateTime.now())
                            .data(Map.of("Genre", genreDTO))
                            .message("All Genres Retrieved")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Genre Not Found")
                        .status(HttpStatus.NOT_FOUND)
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .build());
    }

    @GetMapping("/genres/{name}/books")
    public ResponseEntity<Response> findPublisherBooks(@PathVariable String name) {
        Optional<Genre> optionalGenre = genreService.findByName(name);

        if (optionalGenre.isPresent()) {
            Set<Book> bookSet = bookService.findByGenre(optionalGenre.get());
            List<BookDTO> bookDTOList = bookSet.stream().map(book -> BookDTO.builder()
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
                    .build()).toList();

            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(LocalDateTime.now())
                            .data(Map.of("books", bookDTOList))
                            .message( "Genres´s books retrieved")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Genre Not Found")
                        .status(HttpStatus.NOT_FOUND)
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .build());
    }


    @PostMapping("/genres")
    public ResponseEntity<Response> save(@RequestBody GenreDTO genreDTO) {

        if (genreService.findByName(genreDTO.getName()).isPresent() || genreDTO.getName().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Response.builder()
                            .timeStamp(LocalDateTime.now())
                            .message("Name field is required and must be unique")
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }

        Genre genreToSave = Genre.builder()
                .name(genreDTO.getName())
                .description(genreDTO.getDescription())
                .icon(genreDTO.getIcon())
                .build();

        genreService.save(genreToSave);


        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Genre entry added")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build());
    }

    @PutMapping("/genres/{id}")
    public ResponseEntity<Response> updateById(@PathVariable Long id, @RequestBody GenreDTO genreDTO) {
        Optional<Genre> optionalGenre = genreService.findById(id);

        if (optionalGenre.isEmpty() || genreDTO.getName().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Response.builder()
                            .timeStamp(LocalDateTime.now())
                            .message("Invalid id/name fields")
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());
        }

        Genre genreToUpdate = optionalGenre.get();
        genreToUpdate.setDescription(genreDTO.getDescription());
        genreToUpdate.setIcon(genreDTO.getIcon());
        genreToUpdate.setName(genreDTO.getName());

        genreService.save(genreToUpdate);

        return ResponseEntity.ok(Response.builder()
                .timeStamp(LocalDateTime.now())
                .message("Genre Updated")
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .build());
    }


    @DeleteMapping("/genres/{id}")
    public ResponseEntity<Response> deleteById(@PathVariable Long id) {
        Optional<Genre> optionalGenre = genreService.findById(id);

        if (optionalGenre.isPresent()) {
            genreService.deleteById(optionalGenre.get().getId());
            return ResponseEntity.ok(Response.builder()
                    .timeStamp(LocalDateTime.now())
                    .message("Genre Eliminated")
                    .status(HttpStatus.OK)
                    .statusCode(HttpStatus.OK.value())
                    .build());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Genre Not Found")
                        .status(HttpStatus.NOT_FOUND)
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .build());

    }


}
