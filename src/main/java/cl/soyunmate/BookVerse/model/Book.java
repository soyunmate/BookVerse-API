package cl.soyunmate.BookVerse.model;

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
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String title;
    @Column(unique = true)
    @NotBlank
    private String isbn;
    @NotBlank
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;
    @NotBlank
    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Genre.class)
    @JoinTable(name = "book_genres", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genre;
    private String description;
    private LocalDate publishDate;
    @NotBlank
    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Publisher.class)
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;
    @NotBlank
    private String language;
    private Integer pages;
    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Tag.class)
    @JoinTable(name = "book_tags", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;

    @NotBlank
    private Integer stock;

}
