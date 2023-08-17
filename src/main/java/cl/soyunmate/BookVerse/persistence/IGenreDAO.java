package cl.soyunmate.BookVerse.persistence;

import cl.soyunmate.BookVerse.model.Genre;

import java.util.List;
import java.util.Optional;

public interface IGenreDAO {
    List<Genre> findAll();
    Optional<Genre> findById(Long id);

    Optional<Genre> findByName(String name);

    void save(Genre genre);

    void deleteById(Long id);
}
