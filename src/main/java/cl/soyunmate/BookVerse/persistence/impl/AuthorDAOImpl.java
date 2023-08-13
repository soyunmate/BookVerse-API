package cl.soyunmate.BookVerse.persistence.impl;

import cl.soyunmate.BookVerse.model.Author;
import cl.soyunmate.BookVerse.persistence.IAuthorDAO;
import cl.soyunmate.BookVerse.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component

public class AuthorDAOImpl implements IAuthorDAO {
    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Optional<Author> findById(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public void save(Author author) {
        authorRepository.save(author);
    }

    @Override
    public void deleteById(Long id) {
            authorRepository.deleteById(id);
    }
}
