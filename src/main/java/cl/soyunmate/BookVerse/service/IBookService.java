package cl.soyunmate.BookVerse.service;

import cl.soyunmate.BookVerse.model.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IBookService {
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
