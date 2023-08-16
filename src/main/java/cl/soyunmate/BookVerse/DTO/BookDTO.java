package cl.soyunmate.BookVerse.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class BookDTO {
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
