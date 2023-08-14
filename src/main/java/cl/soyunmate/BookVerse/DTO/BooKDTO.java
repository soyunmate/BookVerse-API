package cl.soyunmate.BookVerse.DTO;

import cl.soyunmate.BookVerse.model.Author;
import cl.soyunmate.BookVerse.model.Genre;
import cl.soyunmate.BookVerse.model.Publisher;
import cl.soyunmate.BookVerse.model.Tag;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BooKDTO {
    private Long id;
    private String title;
    private String isbn;
    private String author;
    private Set<String> genre;
    private String description;
    private LocalDate publishDate;
    private String publisher;
    private String language;
    private Integer pages;
    private Set<String> tags;
    private Integer stock;
}
