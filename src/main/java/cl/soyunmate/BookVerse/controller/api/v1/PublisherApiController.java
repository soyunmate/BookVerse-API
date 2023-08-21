package cl.soyunmate.BookVerse.controller.api.v1;

import cl.soyunmate.BookVerse.DTO.*;
import cl.soyunmate.BookVerse.DTO.mapper.PublisherMapper;
import cl.soyunmate.BookVerse.DTO.mapper.ResponseMapper;
import cl.soyunmate.BookVerse.model.Book;
import cl.soyunmate.BookVerse.model.Genre;
import cl.soyunmate.BookVerse.model.Publisher;
import cl.soyunmate.BookVerse.model.Response;
import cl.soyunmate.BookVerse.model.enums.ETag;
import cl.soyunmate.BookVerse.service.IBookService;
import cl.soyunmate.BookVerse.service.IPublisherService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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
   private ResponseMapper responseMapper;

   @Autowired
   private PublisherMapper publisherMapper;

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
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(responseMapper.toResponse(
                            null,
                            "Invalid Query parameter",
                            "Publisher",
                            HttpStatus.BAD_REQUEST));
        }

        List<PublisherDTO> publisherDTOList= publisherList.stream().map(pb -> publisherMapper.toDto(pb) ).toList();

        return  ResponseEntity.ok(responseMapper.toResponse(publisherDTOList,
                "All Publishers retrieved",
                "Publisher(s)",
                HttpStatus.OK));


    }
    @GetMapping("/publishers/{id}")
    public ResponseEntity<Response> findById(@PathVariable Long id) {

        Optional<Publisher> optionalPublisher = publisherService.findById(id);

        if (optionalPublisher.isPresent()) {
            Publisher publisher = optionalPublisher.get();
            PublisherDTO publisherDTO = publisherMapper.toDto(publisher);

            return  ResponseEntity.ok(responseMapper.toResponse(publisherDTO,
                    "Publisher Retrieved",
                    "Publisher",
                    HttpStatus.OK));

        }

        return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(responseMapper.toResponse(
                        null,
                        "Publisher not found",
                        "Publisher",
                        HttpStatus.NOT_FOUND));
    }



    @PostMapping("/publishers")
    public ResponseEntity<Response> save(@Valid @RequestBody PublisherDTO publisherDTO) {

        if (!publisherDTO.getName().isBlank()) {
            Publisher publisherToSave = publisherMapper.toEntity(publisherDTO);

            publisherService.save(publisherToSave);
            return  ResponseEntity.status(HttpStatus.CREATED)
                    .body(responseMapper.toResponse(
                            null,
                            "Publisher Entry added",
                            "Publisher",
                            HttpStatus.CREATED));
        }

        return  ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(responseMapper.toResponse(
                        null,
                        "Missing one or more required fields",
                        "Publisher",
                        HttpStatus.BAD_REQUEST));
    }

    @PutMapping("/publishers/{id}")
    public ResponseEntity<Response> updateById(@PathVariable Long id, @Valid @RequestBody PublisherDTO publisherDTO) {

        Optional<Publisher> optionalPublisher = publisherService.findById(id);

        if (optionalPublisher.isPresent()) {
            Publisher publisherToUpdate = optionalPublisher.get();
            Publisher mappedPublisher = publisherMapper.toEntity(publisherDTO);
            mappedPublisher.setId(publisherToUpdate.getId());
            publisherService.save(mappedPublisher);

            return  ResponseEntity.ok(responseMapper.toResponse(null,
                    "Publisher Updated",
                    "Publisher",
                    HttpStatus.OK));


        }

        return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(responseMapper.toResponse(
                        null,
                        "Publisher not found",
                        "Publisher",
                        HttpStatus.NOT_FOUND));
    }
    @DeleteMapping("/publishers/{id}")
    public ResponseEntity<Response> deleteById(@PathVariable Long id) {
        Optional<Publisher> optionalPublisher = publisherService.findById(id);

        if (optionalPublisher.isPresent()) {
            publisherService.deleteById(optionalPublisher.get().getId());

            return  ResponseEntity.ok(responseMapper.toResponse(null,
                    "Publisher Eliminated",
                    "Publisher",
                    HttpStatus.OK));
        }

        return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(responseMapper.toResponse(
                        null,
                        "Publisher not found",
                        "Publisher",
                        HttpStatus.NOT_FOUND));

    }

}
