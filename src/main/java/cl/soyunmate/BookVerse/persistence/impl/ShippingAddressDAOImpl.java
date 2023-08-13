package cl.soyunmate.BookVerse.persistence.impl;

import cl.soyunmate.BookVerse.model.ShippingAddress;
import cl.soyunmate.BookVerse.persistence.IShippingAddressDAO;
import cl.soyunmate.BookVerse.repository.ShippingAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ShippingAddressDAOImpl implements IShippingAddressDAO {
    @Autowired
    private ShippingAddressRepository shippingAddressRepository;

    @Override
    public List<ShippingAddress> findAll() {
        return shippingAddressRepository.findAll();
    }

    @Override
    public Optional<ShippingAddress> findById(Long id) {
        return shippingAddressRepository.findById(id);
    }

    @Override
    public void save(ShippingAddress shippingAddress) {
        shippingAddressRepository.save(shippingAddress);
    }

    @Override
    public void deleteById(Long id) {
        shippingAddressRepository.deleteById(id);
    }
}
