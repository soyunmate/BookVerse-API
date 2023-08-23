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
import java.util.List;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(NON_NULL)
public class BookDTO {
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String isbn;
    @NotNull
    private Long authorID;

    private String author;

    @NotEmpty
    private Set<Long> genreIDs;

    private List<String> genres;

    private String description;

    private LocalDate publishDate;
    @NotNull
    private Long publisherID;
    private String publisher;

    @NotBlank
    private String language;

    private Integer pages;
    @NotEmpty
    private Set<Long> tagsIDs;
    private List<String> tags;

    private Integer stock;
}
