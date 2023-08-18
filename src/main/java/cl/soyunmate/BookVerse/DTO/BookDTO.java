package cl.soyunmate.BookVerse.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class BookDTO {
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String isbn;
    @NotNull
    private AuthorDTO author;
    @NotEmpty
    private Set<GenreDTO> genre;

    private String description;

    private LocalDate publishDate;
    @NotNull
    private PublisherDTO publisher;

    @NotBlank
    private String language;

    private Integer pages;
    @NotEmpty
    private Set<TagDTO> tags;

    private Integer stock;
}
