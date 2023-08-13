package cl.soyunmate.BookVerse.persistence;

import cl.soyunmate.BookVerse.model.Publisher;

import java.util.List;
import java.util.Optional;

public interface IPublisherDAO {
    List<Publisher> findAll();
    Optional<Publisher> findById(Long id);

    void save(Publisher publisher);

    void deleteById(Long id);
}
