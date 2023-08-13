package cl.soyunmate.BookVerse.service.impl;

import cl.soyunmate.BookVerse.model.Author;
import cl.soyunmate.BookVerse.persistence.IAuthorDAO;
import cl.soyunmate.BookVerse.service.IAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements IAuthorService {
    @Autowired
    private IAuthorDAO authorDAO;

    @Override
    public List<Author> findAll() {
        return authorDAO.findAll();
    }

    @Override
    public Optional<Author> findById(Long id) {
        return authorDAO.findById(id);
    }

    @Override
    public void save(Author author) {
        authorDAO.save(author);
    }

    @Override
    public void deleteById(Long id) {
        authorDAO.deleteById(id);
    }
}
