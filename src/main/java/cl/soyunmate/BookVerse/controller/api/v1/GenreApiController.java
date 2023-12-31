package cl.soyunmate.BookVerse.controller.api.v1;

import cl.soyunmate.BookVerse.DTO.*;
import cl.soyunmate.BookVerse.DTO.mapper.GenreMapper;
import cl.soyunmate.BookVerse.DTO.mapper.ResponseMapper;
import cl.soyunmate.BookVerse.model.Genre;
import cl.soyunmate.BookVerse.model.Response;
import cl.soyunmate.BookVerse.service.IGenreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class GenreApiController {
    @Autowired
    private IGenreService genreService;

    @Autowired
    private ResponseMapper responseMapper;

    @Autowired
    private GenreMapper genreMapper;

    @Operation(summary = "Retrieve all genres")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved genres", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @GetMapping("/genres")
    public ResponseEntity<Response> findAll() {
        List<Genre> genreList = genreService.findAll();
        List<GenreDTO> genreDTOList = genreList.stream()
                .map(genre -> genreMapper.toDto(genre))
                .toList();

        return  ResponseEntity.ok(responseMapper.toResponse(genreDTOList,
                "All Genres retrieved",
                "Genre(s)",
                HttpStatus.OK));

    }

    @Operation(summary = "Retrieve genre by ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved genre", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "404", description = "Genre not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @GetMapping("/genres/{id}")
    public ResponseEntity<Response> findByid(@PathVariable Long id) {

        Optional<Genre> optionalGenre = genreService.findById(id);

        if (optionalGenre.isPresent()) {
            Genre genre = optionalGenre.get();
            GenreDTO genreDTO = genreMapper.toDto(genre);

            return  ResponseEntity.ok(responseMapper.toResponse(genreDTO,
                    "Genres retrieved",
                    "Genre",
                    HttpStatus.OK));
        }

        return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(responseMapper.toResponse(
                        null,
                        "Genre not found",
                        "Genre",
                        HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Create a new genre entry - Require Admin role")
    @ApiResponse(responseCode = "201", description = "Genre Entry added", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "400", description = "Missing one or more required fields", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/genres")
    public ResponseEntity<Response> save( @Valid @RequestBody GenreDTO genreDTO) {
        try {
            Genre genreToSave = genreMapper.toEntity(genreDTO);
            genreService.save(genreToSave);
            return  ResponseEntity.status(HttpStatus.CREATED)
                    .body(responseMapper.toResponse(
                            null,
                            "Genre Entry added",
                            "Genre",
                            HttpStatus.CREATED));
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(responseMapper.toResponse(
                            null,
                            "Missing one or more required fields",
                            "Genre",
                            HttpStatus.BAD_REQUEST));
        }
    }

    @Operation(summary = "Update genre by ID - Require Admin role")
    @ApiResponse(responseCode = "200", description = "Genre Updated", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "404", description = "Genre not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/genres/{id}")
    public ResponseEntity<Response> updateById(@PathVariable Long id, @Valid @RequestBody GenreDTO genreDTO) {
        Optional<Genre> optionalGenre = genreService.findById(id);

        if (optionalGenre.isPresent()) {
            Genre genreToUpdate = optionalGenre.get();
            Genre mappedGenre = genreMapper.toEntity(genreDTO);
            mappedGenre.setId(genreToUpdate.getId());
            genreService.save(mappedGenre);

            return  ResponseEntity.ok(responseMapper.toResponse(null,
                    "Genre Updated",
                    "Genre",
                    HttpStatus.OK));
        }

            return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(responseMapper.toResponse(
                            null,
                            "Genre not found",
                            "Genre",
                            HttpStatus.NOT_FOUND));
    }


    @Operation(summary = "Delete genre by ID - Require Admin role")
    @ApiResponse(responseCode = "200", description = "Genre Eliminated", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "404", description = "Genre not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)))
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/genres/{id}")
    public ResponseEntity<Response> deleteById(@PathVariable Long id) {
        Optional<Genre> optionalGenre = genreService.findById(id);

        if (optionalGenre.isPresent()) {
            genreService.deleteById(optionalGenre.get().getId());
            return  ResponseEntity.ok(responseMapper.toResponse(null,
                    "Genre Eliminated",
                    "Genre",
                    HttpStatus.OK));
        }

        return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(responseMapper.toResponse(
                        null,
                        "Genre not found",
                        "Genre",
                        HttpStatus.NOT_FOUND));

    }


}
