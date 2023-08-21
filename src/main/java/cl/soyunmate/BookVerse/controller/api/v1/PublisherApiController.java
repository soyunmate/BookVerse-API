package cl.soyunmate.BookVerse.controller.api.v1;

import cl.soyunmate.BookVerse.DTO.*;
import cl.soyunmate.BookVerse.model.Book;
import cl.soyunmate.BookVerse.model.Genre;
import cl.soyunmate.BookVerse.model.Publisher;
import cl.soyunmate.BookVerse.model.Response;
import cl.soyunmate.BookVerse.model.enums.ETag;
import cl.soyunmate.BookVerse.service.IBookService;
import cl.soyunmate.BookVerse.service.IPublisherService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class PublisherApiController {
    @Autowired
    private IPublisherService publisherService;

    @Autowired
    private IBookService bookService;

    @GetMapping("/publishers")
    public ResponseEntity<Response> findAll(@Parameter(description = "Filter by publisher name") @RequestParam(required = false, defaultValue = "") String publisher,
                                            HttpServletRequest request) {
        List<Publisher> publisherList = publisherService.findAll();

        boolean passedAnyFilter = false;

        if (!publisher.isBlank()) {
            publisherList = publisherList.stream().filter(pb -> pb.getName().toLowerCase().equals(publisher)).toList();
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

        List<PublisherDTO> publisherDTOList= publisherList.stream().map(pb -> PublisherDTO
                .builder()
                        .id(pb.getId())
                        .name(pb.getName())
                        .description(pb.getDescription())
                        .webSite(pb.getWebSite())
                        .build())
                .toList();

        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("Publishers", publisherDTOList))
                        .message("All Publishers Retrieved")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());


    }
    @GetMapping("/publishers/{id}")
    public ResponseEntity<Response> findById(@PathVariable Long id) {

        Optional<Publisher> optionalPublisher = publisherService.findById(id);

        if (optionalPublisher.isPresent()) {
            Publisher publisher = optionalPublisher.get();
            PublisherDTO publisherDTO = PublisherDTO.builder()
                    .id(publisher.getId())
                    .name(publisher.getName())
                    .description(publisher.getDescription())
                    .webSite(publisher.getWebSite())
                    .build();

            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(LocalDateTime.now())
                            .data(Map.of("Publisher", publisherDTO))
                            .message("Publishers Retrieved")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build());

        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Publisher Not Found")
                        .status(HttpStatus.NOT_FOUND)
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .build());
    }

    @GetMapping("/publishers/{id}/books")
    public ResponseEntity<Response> findPublisherBooks(@PathVariable Long id) {
        Optional<Publisher> optionalPublisher = publisherService.findById(id);

        if (optionalPublisher.isPresent()) {
            Set<Book> bookSet = bookService.findByPublisher(optionalPublisher.get());
            List<BookDTO> bookDTOList = bookSet.stream().map(book -> BookDTO.builder()
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
                    .build()).toList();

            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(LocalDateTime.now())
                            .data(Map.of("books", bookDTOList))
                            .message( "Publisher´s books retrieved")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Publisher Not Found")
                        .status(HttpStatus.NOT_FOUND)
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .build());
    }

    @PostMapping("/publishers")
    public ResponseEntity<Response> save(@RequestBody PublisherDTO publisherDTO) {

        if (!publisherDTO.getName().isBlank()) {
            Publisher publisherToSave = Publisher.builder()
                    .name(publisherDTO.getName())
                    .description(publisherDTO.getDescription())
                    .webSite(publisherDTO.getWebSite())
                    .build();

            publisherService.save(publisherToSave);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Response.builder()
                            .timeStamp(LocalDateTime.now())
                            .message("Publisher Entry Added")
                            .status(HttpStatus.CREATED)
                            .statusCode(HttpStatus.CREATED.value())
                            .build());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("New Publishers need to at least have a name!")
                        .status(HttpStatus.BAD_REQUEST)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .build());
    }

    @PutMapping("/publishers/{id}")
    public ResponseEntity<Response> updateById(@PathVariable Long id, @RequestBody PublisherDTO publisherDTO) {

        Optional<Publisher> optionalPublisher = publisherService.findById(id);

        if (optionalPublisher.isPresent()) {
            if(publisherDTO.getName().isBlank()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Response.builder()
                                .timeStamp(LocalDateTime.now())
                                .message("Publishers need to at least have a name!")
                                .status(HttpStatus.BAD_REQUEST)
                                .statusCode(HttpStatus.BAD_REQUEST.value())
                                .build());
            }

            Publisher publisherToUpdate = optionalPublisher.get();
            publisherToUpdate.setName(publisherDTO.getName());
            publisherToUpdate.setDescription(publisherDTO.getDescription());
            publisherToUpdate.setWebSite(publisherDTO.getWebSite());

            publisherService.save(publisherToUpdate);
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(LocalDateTime.now())
                            .message("Publisher Updated")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build());


        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Publisher Not Found")
                        .status(HttpStatus.NOT_FOUND)
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .build());
    }
    @DeleteMapping("/publishers/{id}")
    public ResponseEntity<Response> deleteById(@PathVariable Long id) {
        Optional<Publisher> optionalPublisher = publisherService.findById(id);

        if (optionalPublisher.isPresent()) {
            publisherService.deleteById(optionalPublisher.get().getId());

            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(LocalDateTime.now())
                            .message("Publisher Eliminated")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Response.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Cannot Eliminate a non-existent entry")
                        .status(HttpStatus.NOT_FOUND)
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .build());

    }

}
