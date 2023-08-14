package cl.soyunmate.BookVerse.controller.api.v1;

import cl.soyunmate.BookVerse.DTO.BooKDTO;
import cl.soyunmate.BookVerse.model.Book;
import cl.soyunmate.BookVerse.model.Genre;
import cl.soyunmate.BookVerse.model.Response;
import cl.soyunmate.BookVerse.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/books")
public class BookApiController {
    @Autowired
    private IBookService bookService;

    @GetMapping("/list")
    public ResponseEntity<Response> getAllBooks() {
        List<Book> bookList = bookService.findAll();
        List<BooKDTO> bookDTOList = bookList.stream()
                .map(book -> BooKDTO.builder()
                        .id(book.getId())
                        .isbn(book.getIsbn())
                        .title(book.getTitle())
                        .author(book.getAuthor().getFirstName() + " " + book.getAuthor().getLastName())
                        .genre(book.getGenre().stream().map(Genre::getName).collect(Collectors.toSet()))
                        .description(book.getDescription())
                        .publishDate(book.getPublishDate())
                        .publisher(book.getPublisher().getName())
                        .language(book.getLanguage())
                        .pages(book.getPages())
                        .tags(book.getTags().stream().map(tag -> tag.getName().name()).collect(Collectors.toSet()))
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

}
