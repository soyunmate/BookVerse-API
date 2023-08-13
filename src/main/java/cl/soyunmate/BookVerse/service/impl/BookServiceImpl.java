package cl.soyunmate.BookVerse.service.impl;

import cl.soyunmate.BookVerse.model.*;
import cl.soyunmate.BookVerse.persistence.IBookDAO;
import cl.soyunmate.BookVerse.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class BookServiceImpl implements IBookService {
    @Autowired
    private IBookDAO bookDAO;

    @Override
    public List<Book> findAll() {
        return bookDAO.findAll();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookDAO.findById(id);
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return bookDAO.findByIsbn(isbn);
    }

    @Override
    public Set<Book> findByAuthor(Author author) {
        return bookDAO.findByAuthor(author);
    }

    @Override
    public Set<Book> findByGenre(Genre genre) {
        return bookDAO.findByGenre(genre);
    }

    @Override
    public Set<Book> findByPublisher(Publisher publisher) {
        return bookDAO.findByPublisher(publisher);
    }

    @Override
    public Set<Book> findByLanguage(String language) {
        return bookDAO.findByLanguage(language);
    }

    @Override
    public Set<Book> findByTag(Tag tag) {
        return bookDAO.findByTag(tag);
    }

    @Override
    public List<Book> findByTitleContainingPattern(String pattern) {
        return bookDAO.findByTitleContainingPattern(pattern);
    }

    @Override
    public List<Book> findByAuthorNameContainingPattern(String authorPattern) {
        return bookDAO.findByAuthorNameContainingPattern(authorPattern);
    }

    @Override
    public void save(Book book) {
        bookDAO.save(book);
    }

    @Override
    public void deleteById(Long id) {
        bookDAO.deleteById(id);
    }
}
