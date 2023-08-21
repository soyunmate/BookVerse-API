package cl.soyunmate.BookVerse.service.impl;

import cl.soyunmate.BookVerse.model.Tag;
import cl.soyunmate.BookVerse.model.enums.ETag;
import cl.soyunmate.BookVerse.persistence.ITagDAO;
import cl.soyunmate.BookVerse.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements ITagService {
    @Autowired
    private ITagDAO tagDAO;

    @Override
    public List<Tag> findAll() {
        return tagDAO.findAll();
    }

    @Override
    public Optional<Tag> findByName(ETag name) {
        return tagDAO.findByName(name);
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return tagDAO.findById(id);
    }

    @Override
    public void save(Tag tag) {
        tagDAO.save(tag);
    }

    @Override
    public void deleteById(Long id) {
        tagDAO.deleteById(id);
    }
}
