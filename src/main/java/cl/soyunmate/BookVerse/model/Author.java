package cl.soyunmate.BookVerse.model;

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
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String firstName;
    @NotBlank
    private  String lastName;

    @OneToMany(fetch = FetchType.EAGER, targetEntity = Book.class)
    @JoinTable(name = "author_books", joinColumns = @JoinColumn(name = "author_id"), inverseJoinColumns = @JoinColumn(name = "book_id"))
    private Set<Book> publishedBooks;
    private String nationality;
    private String birthDate;
    private String biography;
}
