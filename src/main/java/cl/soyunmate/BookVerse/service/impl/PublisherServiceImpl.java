package cl.soyunmate.BookVerse.service.impl;

import cl.soyunmate.BookVerse.model.Publisher;
import cl.soyunmate.BookVerse.persistence.IPublisherDAO;
import cl.soyunmate.BookVerse.service.IPublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublisherServiceImpl implements IPublisherService {
    @Autowired
    private IPublisherDAO publisherDAO;

    @Override
    public List<Publisher> findAll() {
        return publisherDAO.findAll();
    }

    @Override
    public Optional<Publisher> findById(Long id) {
        return publisherDAO.findById(id);
    }

    @Override
    public void save(Publisher publisher) {
        publisherDAO.save(publisher);
    }

    @Override
    public void deleteById(Long id) {
        publisherDAO.deleteById(id);
    }
}
