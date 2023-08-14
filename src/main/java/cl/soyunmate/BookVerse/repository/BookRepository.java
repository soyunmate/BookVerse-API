package cl.soyunmate.BookVerse.repository;

import cl.soyunmate.BookVerse.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Set<Book> findByAuthor(Author author);

    @Query("SELECT DISTINCT b FROM Book b JOIN b.genre g WHERE g = :genre")
    Set<Book> findByGenre(@Param("genre") Genre genre);

    Set<Book> findByPublisher(Publisher publisher);

    Set<Book> findByLanguage(String language);


    @Query("SELECT DISTINCT b FROM Book b JOIN b.tags t WHERE t = :tag")
    Set<Book> findByTag(@Param("tag") Tag tag);

    @Query("SELECT b FROM Book b WHERE b.title LIKE %:pattern%")
    List<Book> findByTitleContainingPattern(@Param("pattern") String pattern);

    @Query("SELECT b FROM Book b WHERE b.author.firstName LIKE %:authorPattern% OR b.author.lastName LIKE %:authorPattern%")
    List<Book> findByAuthorNameContainingPattern(@Param("authorPattern") String authorPattern);


    Optional<Book>findByIsbn(String isbn);
}
