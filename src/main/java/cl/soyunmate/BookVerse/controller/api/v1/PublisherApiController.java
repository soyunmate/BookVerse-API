package cl.soyunmate.BookVerse.controller.api.v1;

import cl.soyunmate.BookVerse.DTO.PublisherDTO;
import cl.soyunmate.BookVerse.model.Publisher;
import cl.soyunmate.BookVerse.model.Response;
import cl.soyunmate.BookVerse.service.IPublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class PublisherApiController {
    @Autowired
    private IPublisherService publisherService;

    @GetMapping("/publishers")
    public ResponseEntity<Response> findAll() {
        List<Publisher> publisherList = publisherService.findAll();
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
