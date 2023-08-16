package cl.soyunmate.BookVerse.DTO;

import cl.soyunmate.BookVerse.model.enums.EGenre;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenreDTO {

    private Long id;
    private EGenre name;
    private String description;
    private String icon;
    private Set<BookDTO> books;
}
