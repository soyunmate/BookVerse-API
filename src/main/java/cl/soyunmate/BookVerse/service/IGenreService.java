package cl.soyunmate.BookVerse.service;

import cl.soyunmate.BookVerse.model.Genre;

import java.util.List;
import java.util.Optional;

public interface IGenreService {

    List<Genre> findAll();
    Optional<Genre> findById(Long id);

    void save(Genre genre);

    void deleteById(Long id);
}
