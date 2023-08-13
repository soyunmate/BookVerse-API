package cl.soyunmate.BookVerse.service.impl;

import cl.soyunmate.BookVerse.model.Genre;
import cl.soyunmate.BookVerse.persistence.IGenreDAO;
import cl.soyunmate.BookVerse.service.IGenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements IGenreService {
    @Autowired
    private IGenreDAO genreDAO;

    @Override
    public List<Genre> findAll() {
        return genreDAO.findAll();
    }

    @Override
    public Optional<Genre> findById(Long id) {
        return genreDAO.findById(id);
    }

    @Override
    public void save(Genre genre) {
        genreDAO.save(genre);
    }

    @Override
    public void deleteById(Long id) {
        genreDAO.deleteById(id);
    }
}
