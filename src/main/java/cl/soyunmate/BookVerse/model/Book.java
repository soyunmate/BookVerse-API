package cl.soyunmate.BookVerse.model;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    private String title;
    @Column(unique = true)
    private String isbn;
    @ManyToOne(targetEntity = Author.class, fetch = FetchType.EAGER)
    /*@JoinTable(name = "author_books", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "author_id"))*/
    @JoinColumn(name = "author_id",nullable = false)
    private Author author;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Genre.class)
    @JoinTable(name = "book_genres", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genre;
    private String description;
    private LocalDate publishDate;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Publisher.class)
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    private String language;
    private Integer pages;
    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Tag.class)
    @JoinTable(name = "book_tags", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;


    private Integer stock;

}
