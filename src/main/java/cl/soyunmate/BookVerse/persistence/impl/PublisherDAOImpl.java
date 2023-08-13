package cl.soyunmate.BookVerse.persistence.impl;

import cl.soyunmate.BookVerse.model.Publisher;
import cl.soyunmate.BookVerse.persistence.IPublisherDAO;
import cl.soyunmate.BookVerse.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
public class PublisherDAOImpl implements IPublisherDAO {
    @Autowired
    private PublisherRepository publisherRepository;

    @Override
    public List<Publisher> findAll() {
        return publisherRepository.findAll();
    }

    @Override
    public Optional<Publisher> findById(Long id) {
        return publisherRepository.findById(id);
    }

    @Override
    public void save(Publisher publisher) {
        publisherRepository.save(publisher);
    }

    @Override
    public void deleteById(Long id) {
        publisherRepository.deleteById(id);
    }
}
