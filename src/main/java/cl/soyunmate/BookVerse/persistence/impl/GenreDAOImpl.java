package cl.soyunmate.BookVerse.persistence.impl;

import cl.soyunmate.BookVerse.model.Genre;
import cl.soyunmate.BookVerse.persistence.IGenreDAO;
import cl.soyunmate.BookVerse.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GenreDAOImpl implements IGenreDAO {
    @Autowired
    private GenreRepository genreRepository;

    @Override
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    @Override
    public Optional<Genre> findById(Long id) {
        return genreRepository.findById(id);
    }

    @Override
    public void save(Genre genre) {
        genreRepository.save(genre);
    }

    @Override
    public void deleteById(Long id) {
            genreRepository.deleteById(id);
    }
}
