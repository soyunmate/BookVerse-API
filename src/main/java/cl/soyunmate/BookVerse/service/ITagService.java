package cl.soyunmate.BookVerse.service;

import cl.soyunmate.BookVerse.model.Tag;
import cl.soyunmate.BookVerse.model.enums.ETag;

import java.util.List;
import java.util.Optional;

public interface ITagService {

    List<Tag> findAll();

    Optional<Tag> findByName(ETag name);
    Optional<Tag> findById(Long id);

    void save(Tag tag);

    void deleteById(Long id);
}
