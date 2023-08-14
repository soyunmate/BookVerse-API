package cl.soyunmate.BookVerse.persistence.impl;

import cl.soyunmate.BookVerse.model.*;
import cl.soyunmate.BookVerse.persistence.IBookDAO;
import cl.soyunmate.BookVerse.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class BookDAOImpl implements IBookDAO {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    @Override
    public Set<Book> findByAuthor(Author author) {
        return bookRepository.findByAuthor(author);
    }

    @Override
    public Set<Book> findByGenre(Genre genre) {
        return bookRepository.findByGenre(genre);
    }

    @Override
    public Set<Book> findByPublisher(Publisher publisher) {
        return bookRepository.findByPublisher(publisher);
    }

    @Override
    public Set<Book> findByLanguage(String language) {
        return bookRepository.findByLanguage(language);
    }

    @Override
    public Set<Book> findByTag(Tag tag) {
        return bookRepository.findByTag(tag);
    }

    @Override
    public List<Book> findByTitleContainingPattern(String pattern) {
        return bookRepository.findByTitleContainingPattern(pattern);
    }

    @Override
    public List<Book> findByAuthorNameContainingPattern(String authorPattern) {
        return bookRepository.findByAuthorNameContainingPattern(authorPattern);
    }

    @Override
    public void save(Book book) {
        bookRepository.save(book);
    }


    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}
