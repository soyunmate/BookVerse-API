package cl.soyunmate.BookVerse.persistence.impl;

import cl.soyunmate.BookVerse.model.Tag;
import cl.soyunmate.BookVerse.model.enums.ETag;
import cl.soyunmate.BookVerse.persistence.ITagDAO;
import cl.soyunmate.BookVerse.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TagDAOImpl implements ITagDAO {
    @Autowired
    private TagRepository tagRepository;

    @Override
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return tagRepository.findById(id);
    }

    @Override
    public Optional<Tag> findByName(ETag name) {
        return tagRepository.findByName(name);
    }

    @Override
    public void save(Tag tag) {
        tagRepository.save(tag);
    }

    @Override
    public void deleteById(Long id) {
        tagRepository.deleteById(id);
    }
}
