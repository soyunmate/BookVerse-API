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
@Entity
@Builder
@Table(name = "publishers")
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String webSite;
    private String description;
    /*@OneToMany(fetch = FetchType.EAGER, targetEntity = Book.class)
    @JoinTable(name = "publisher_books", joinColumns = @JoinColumn(name = "publisher_id"), inverseJoinColumns = @JoinColumn(name = "book_id"))
    private Set<Book> bookSet;*/

}
