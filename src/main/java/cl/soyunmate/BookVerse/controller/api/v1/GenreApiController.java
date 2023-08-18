package cl.soyunmate.BookVerse.controller.api.v1;

import cl.soyunmate.BookVerse.DTO.GenreDTO;
import cl.soyunmate.BookVerse.model.Genre;
import cl.soyunmate.BookVerse.model.Response;
import cl.soyunmate.BookVerse.service.IGenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@RestController
@RequestMapping("/api/v1")
public class GenreApiController {
    @Autowired
    private IGenreService genreService;
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
