package cl.soyunmate.BookVerse.persistence;

import cl.soyunmate.BookVerse.model.Tag;

import java.util.List;
import java.util.Optional;

public interface ITagDAO {
    List<Tag> findAll();

    Optional<Tag> findById(Long id);

    void save(Tag tag);

    void deleteById(Long id);
}
