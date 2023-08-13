package cl.soyunmate.BookVerse.persistence;

import cl.soyunmate.BookVerse.model.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IBookDAO {

    List<Book> findAll();
    Optional<Book> findById(Long id);
    Optional<Book> findByIsbn(String isbn);

    Set<Book> findByAuthor(Author author);

    Set<Book> findByGenre(Genre genre);

    Set<Book> findByPublisher(Publisher publisher);

    Set<Book> findByLanguage(String language);

    Set<Book> findByTag(Tag tag);

    List<Book> findByTitleContainingPattern(String pattern);

    List<Book> findByAuthorNameContainingPattern(String authorPattern);

    void save(Book book);

    void deleteById(Long id);


}
