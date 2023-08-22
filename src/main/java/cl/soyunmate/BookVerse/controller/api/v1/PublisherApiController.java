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
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Operation(summary = "Find publishers with filters")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved publishers", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "400", description = "Invalid Query parameter", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
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
    @Operation(summary = "Find publisher by ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved publisher", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "404", description = "Publisher not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
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


    @Operation(summary = "Create a new publisher entry - Require Admin role")
    @ApiResponse(responseCode = "201", description = "Publisher Entry added", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "400", description = "Missing one or more required fields", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/publishers")
    public ResponseEntity<Response> save(@Valid @RequestBody PublisherDTO publisherDTO) {

        try {
            Publisher publisherToSave = publisherMapper.toEntity(publisherDTO);
            publisherService.save(publisherToSave);
            return  ResponseEntity.status(HttpStatus.CREATED)
                    .body(responseMapper.toResponse(
                            null,
                            "Publisher Entry added",
                            "Publisher",
                            HttpStatus.CREATED));
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(responseMapper.toResponse(
                            null,
                            "Missing one or more required fields",
                            "Publisher",
                            HttpStatus.BAD_REQUEST));
        }
    }

    @Operation(summary = "Update publisher by ID - Require Admin role")
    @ApiResponse(responseCode = "200", description = "Publisher Updated", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "404", description = "Publisher not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @PreAuthorize("hasRole('ADMIN')")
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
    @Operation(summary = "Delete publisher by ID - Require Admin role")
    @ApiResponse(responseCode = "200", description = "Publisher Eliminated", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "404", description = "Publisher not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @PreAuthorize("hasRole('ADMIN')")
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
