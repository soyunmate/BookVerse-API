package cl.soyunmate.BookVerse.DTO;

import cl.soyunmate.BookVerse.model.Book;
import cl.soyunmate.BookVerse.model.enums.EGenre;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
@JsonInclude(NON_NULL)
public class GenreDTO {

    private Long id;
    private EGenre name;
    private String description;
    private String icon;
    private Set<BooKDTO> books;
}
