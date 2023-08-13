package cl.soyunmate.BookVerse.service;

import cl.soyunmate.BookVerse.model.Tag;

import java.util.List;
import java.util.Optional;

public interface ITagService {

    List<Tag> findAll();

    Optional<Tag> findById(Long id);

    void save(Tag tag);

    void deleteById(Long id);
}
