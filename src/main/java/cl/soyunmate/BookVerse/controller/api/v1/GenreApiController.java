package cl.soyunmate.BookVerse.controller.api.v1;

import cl.soyunmate.BookVerse.DTO.*;
import cl.soyunmate.BookVerse.DTO.mapper.GenreMapper;
import cl.soyunmate.BookVerse.DTO.mapper.ResponseMapper;
import cl.soyunmate.BookVerse.model.Book;
import cl.soyunmate.BookVerse.model.Genre;
import cl.soyunmate.BookVerse.model.Publisher;
import cl.soyunmate.BookVerse.model.Response;
import cl.soyunmate.BookVerse.model.enums.ETag;
import cl.soyunmate.BookVerse.service.IBookService;
import cl.soyunmate.BookVerse.service.IGenreService;
import jakarta.validation.Valid;
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
    private ResponseMapper responseMapper;

    @Autowired
    private GenreMapper genreMapper;

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

    @PostMapping("/genres")
    public ResponseEntity<Response> save( @Valid @RequestBody GenreDTO genreDTO) {

        Genre genreToSave = genreMapper.toEntity(genreDTO);
        genreService.save(genreToSave);


        return  ResponseEntity.status(HttpStatus.CREATED)
                .body(responseMapper.toResponse(
                        null,
                        "Genre Entry added",
                        "Genre",
                        HttpStatus.CREATED));
    }

    @PutMapping("/genres/{id}")
    public ResponseEntity<Response> updateById(@PathVariable Long id, @Valid @RequestBody GenreDTO genreDTO) {
        Optional<Genre> optionalGenre = genreService.findById(id);

        if (optionalGenre.isPresent()) {
            Genre genreToUpdate = optionalGenre.get();
            Genre mappedGenre = genreMapper.toEntity(genreDTO);
            mappedGenre.setId(genreToUpdate.getId());
            genreService.save(genreToUpdate);

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
