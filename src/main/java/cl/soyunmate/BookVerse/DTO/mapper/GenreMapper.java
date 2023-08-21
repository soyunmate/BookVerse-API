package cl.soyunmate.BookVerse.DTO.mapper;

import cl.soyunmate.BookVerse.DTO.GenreDTO;
import cl.soyunmate.BookVerse.DTO.PublisherDTO;
import cl.soyunmate.BookVerse.model.Genre;
import cl.soyunmate.BookVerse.model.Publisher;
import org.springframework.stereotype.Component;

@Component
public class GenreMapper {
    public GenreDTO toDto(Genre genre) {
        return GenreDTO.builder()
                .id(genre.getId())
                .name(genre.getName())
                .description(genre.getDescription())
                .icon(genre.getIcon())
                .build();
    }

    public Genre toEntity(GenreDTO genreDTO) {
        return Genre.builder()
                .name(genreDTO.getName())
                .description(genreDTO.getDescription())
                .icon(genreDTO.getIcon())
                .build();
    }
}
