package cl.soyunmate.BookVerse.model;

import cl.soyunmate.BookVerse.model.enums.EGenre;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "genres")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EGenre name;
    private String description;
    private String icon;
    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Book.class)
    private Set<Book> books;
}
