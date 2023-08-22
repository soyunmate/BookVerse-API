package cl.soyunmate.BookVerse.controller.api.v1;

import cl.soyunmate.BookVerse.DTO.*;
import cl.soyunmate.BookVerse.DTO.mapper.AuthorMapper;
import cl.soyunmate.BookVerse.DTO.mapper.ResponseMapper;
import cl.soyunmate.BookVerse.model.Author;
import cl.soyunmate.BookVerse.model.Book;
import cl.soyunmate.BookVerse.model.Genre;
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
import org.springframework.security.access.prepost.PreAuthorize;
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
   private AuthorMapper authorMapper;

   @Autowired
   private ResponseMapper responseMapper;

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
           passedAnyFilter = true;
        }

        if (!passedAnyFilter && !request.getParameterMap().isEmpty()) {

            return  ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(responseMapper.toResponse(
                            null,
                            "Invalid Query Parameter",
                            "Author",
                            HttpStatus.BAD_REQUEST));

        }

        List<AuthorDTO> authorDTOList = authorList.stream().map( au -> authorMapper.toDto(au)).toList();

        return  ResponseEntity.ok(responseMapper.toResponse(authorDTOList,
                "All Authors retrieved",
                "Author(s)",
                HttpStatus.OK));

    }

    @Operation(summary = "Find author by ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved author", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "404", description = "Author not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @GetMapping("/authors/{id}")
    public ResponseEntity<Response> findById(@Parameter(description = "ID of the author to be obtained") @PathVariable Long id) {
        Optional<Author> optionalAuthor = authorService.findById(id);

        if (optionalAuthor.isPresent()) {
            Author author = optionalAuthor.get();
            AuthorDTO authorDTO = authorMapper.toDto(author);

            return  ResponseEntity.ok(responseMapper.toResponse(authorDTO,
                    "Authors retrieved",
                    "Author",
                    HttpStatus.OK));

        }

        return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(responseMapper.toResponse(
                        null,
                        "Author not found",
                        "Author",
                        HttpStatus.NOT_FOUND));
    }


    @Operation(summary = "Create a new author - Require Admin role")
    @ApiResponse(responseCode = "201", description = "Author Created", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "400", description = "Missing one or more required fields", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/authors")
    public ResponseEntity<Response> save( @Valid @RequestBody AuthorDTO authorDTO) {

        try {
            Author authorToSave = authorMapper.toEntity(authorDTO);

            authorService.save(authorToSave);
            return  ResponseEntity.status(HttpStatus.CREATED)
                    .body(responseMapper.toResponse(
                            null,
                            "Author Entry added",
                            "Author",
                            HttpStatus.CREATED));

        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(responseMapper.toResponse(
                            null,
                            "Missing one or more required fields",
                            "Author",
                            HttpStatus.BAD_REQUEST));

        }

    }

    @Operation(summary = "Update author by ID - Require Admin role")
    @ApiResponse(responseCode = "200", description = "Author Updated", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "404", description = "Author not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/authors/{id}")
    public ResponseEntity<Response> updateById(@Parameter(description = "ID of the author to be updated") @PathVariable Long id,
                                               @Valid @RequestBody AuthorDTO authorDTO) {
        Optional<Author> optionalAuthor = authorService.findById(id);

        if (optionalAuthor.isPresent()) {
            Author updatedAuthor = optionalAuthor.get();
            Author mappedAuthor = authorMapper.toEntity(authorDTO);
            mappedAuthor.setId(updatedAuthor.getId());
            authorService.save(mappedAuthor);

            return  ResponseEntity.ok(responseMapper.toResponse(null,
                    "Authors Updated",
                    "Author",
                    HttpStatus.OK));

        }

        return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(responseMapper.toResponse(
                        null,
                        "Author not found",
                        "Author",
                        HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Delete author by ID - Require Admin role")
    @ApiResponse(responseCode = "200", description = "Author Eliminated", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "404", description = "Author not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/authors/{id}")
    public ResponseEntity<Response> deleteById(@Parameter(description = "ID of the author to be deleted") @PathVariable Long id) {

        Optional<Author> optionalAuthor = authorService.findById(id);

        if (optionalAuthor.isPresent()) {
            authorService.deleteById(optionalAuthor.get().getId());
            return  ResponseEntity.ok(responseMapper.toResponse(null,
                    "Authors Eliminated",
                    "Author",
                    HttpStatus.OK));
        }

        return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(responseMapper.toResponse(
                        null,
                        "Author not found",
                        "Author",
                        HttpStatus.NOT_FOUND));
    }

}
