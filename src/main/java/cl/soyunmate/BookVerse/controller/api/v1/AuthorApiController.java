package cl.soyunmate.BookVerse.controller.api.v1;

import cl.soyunmate.BookVerse.DTO.AuthorDTO;
import cl.soyunmate.BookVerse.DTO.BooKDTO;
import cl.soyunmate.BookVerse.model.Author;
import cl.soyunmate.BookVerse.model.Book;
import cl.soyunmate.BookVerse.model.Response;
import cl.soyunmate.BookVerse.service.IAuthorService;
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
@RequestMapping("/api/v1/authors")
public class AuthorApiController {
    @Autowired
    private IAuthorService authorService;
    @GetMapping("/findAll")
    public ResponseEntity<Response> findAll() {

        List<Author> authorList = authorService.findAll();
        List<AuthorDTO> authorDTOList = authorList.stream().map( au -> AuthorDTO
                        .builder()
                        .id(au.getId())
                        .firstName(au.getFirstName())
                        .lastName(au.getLastName())
                        .nationality(au.getNationality())
                        .birthDate(au.getBirthDate())
                        .biography(au.getBiography())
                        .publishedBooks(au.getPublishedBooks().stream().map(book -> BooKDTO
                                .builder()
                                .id(book.getId())
                                .title(book.getTitle())
                                .isbn(book.getIsbn())
                                .build()).collect(Collectors.toSet()))
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

    @GetMapping("/find/{id}")
    public ResponseEntity<Response> findById(@PathVariable Long id) {
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
                    .publishedBooks(author.getPublishedBooks().stream().map(book -> BooKDTO
                            .builder()
                            .id(book.getId())
                            .title(book.getTitle())
                            .isbn(book.getIsbn())
                            .build()).collect(Collectors.toSet()))
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
    @PostMapping("/save")
    public ResponseEntity<Response> save(@RequestBody AuthorDTO authorDTO) {

        try {
            Author authorToSave = Author.builder()
                    .firstName(authorDTO.getFirstName())
                    .lastName(authorDTO.getLastName())
                    .nationality(authorDTO.getNationality())
                    .birthDate(authorDTO.getBirthDate())
                    .biography(authorDTO.getBiography())
                    .publishedBooks(authorDTO.getPublishedBooks().stream().map(book -> Book
                            .builder()
                            .id(book.getId())
                            .build()).collect(Collectors.toSet()))
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
                            .message(e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build());

        }

    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateById(@PathVariable Long id, @RequestBody AuthorDTO authorDTO) {
        Optional<Author> optionalAuthor = authorService.findById(id);

        if (optionalAuthor.isPresent()) {
            try {
                Author updatedAuthor = optionalAuthor.get();
                updatedAuthor.setFirstName(authorDTO.getFirstName());
                updatedAuthor.setLastName(authorDTO.getLastName());
                updatedAuthor.setNationality(authorDTO.getNationality());
                updatedAuthor.setBiography(authorDTO.getBiography());
                updatedAuthor.setNationality(authorDTO.getNationality());
                //TODO no esta funcionando correctamente
                updatedAuthor.setPublishedBooks(authorDTO.getPublishedBooks().stream()
                        .map(bDTO -> Book.builder().id(bDTO.getId()).build()).collect(Collectors.toSet()));

                authorService.save(updatedAuthor);

                return ResponseEntity.status(HttpStatus.OK).body(Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Author Updated")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
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

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Author Not Found")
                        .status(HttpStatus.NOT_FOUND)
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .build());
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteById(@PathVariable Long id) {

        Optional<Author> optionalAuthor = authorService.findById(id);
        //TODO cambiar el "no content" por otro httpstatus mejor
        if (optionalAuthor.isPresent()) {
            authorService.deleteById(optionalAuthor.get().getId());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Response.builder()
                    .timeStamp(LocalDateTime.now())
                    .message("Author Eliminated")
                    .status(HttpStatus.NO_CONTENT)
                    .statusCode(HttpStatus.NO_CONTENT.value())
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
