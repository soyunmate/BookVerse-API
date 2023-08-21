package cl.soyunmate.BookVerse.DTO.mapper;

import cl.soyunmate.BookVerse.DTO.AuthorDTO;
import cl.soyunmate.BookVerse.DTO.BookDTO;
import cl.soyunmate.BookVerse.model.*;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
@Component
public class AuthorMapper {
    public AuthorDTO toDto(Author author) {
        return AuthorDTO.builder()
                .id(author.getId())
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .nationality(author.getNationality())
                .birthDate(author.getBirthDate())
                .biography(author.getBiography())
                .build();
    }

    public Author toEntity(AuthorDTO authorDTO) {
        return Author.builder()
                .firstName(authorDTO.getFirstName())
                .lastName(authorDTO.getLastName())
                .nationality(authorDTO.getNationality())
                .birthDate(authorDTO.getBirthDate())
                .biography(authorDTO.getBiography())
                .build();
    }
}
