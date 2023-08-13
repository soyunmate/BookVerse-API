package cl.soyunmate.BookVerse.persistence;

import cl.soyunmate.BookVerse.model.Author;

import java.util.List;
import java.util.Optional;

public interface IAuthorDAO {

    List<Author> findAll();
    Optional<Author> findById(Long id);
    void save(Author author);
    void deleteById(Long id);

}
