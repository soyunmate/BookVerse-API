package cl.soyunmate.BookVerse.DTO;

import cl.soyunmate.BookVerse.model.Author;
import cl.soyunmate.BookVerse.model.Genre;
import cl.soyunmate.BookVerse.model.Publisher;
import cl.soyunmate.BookVerse.model.Tag;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(NON_NULL)
public class BooKDTO {
    private Long id;
    @NotEmpty
    private String title;
    @NotEmpty
    private String isbn;

    private AuthorDTO author;

    private Set<GenreDTO> genre;
    @NotEmpty
    private String description;
    @NotNull
    private LocalDate publishDate;

    private PublisherDTO publisher;
    @NotEmpty
    private String language;

    private Integer pages;
    private Set<TagDTO> tags;

    private Integer stock;
}
