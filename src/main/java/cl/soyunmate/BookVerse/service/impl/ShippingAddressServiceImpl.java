package cl.soyunmate.BookVerse.service.impl;

import cl.soyunmate.BookVerse.model.ShippingAddress;
import cl.soyunmate.BookVerse.persistence.IShippingAddressDAO;
import cl.soyunmate.BookVerse.service.IShippingAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShippingAddressServiceImpl implements IShippingAddressService {
    @Autowired
    private IShippingAddressDAO shippingAddressDAO;

    @Override
    public List<ShippingAddress> findAll() {
        return shippingAddressDAO.findAll();
    }

    @Override
    public Optional<ShippingAddress> findById(Long id) {
        return shippingAddressDAO.findById(id);
    }

    @Override
    public void save(ShippingAddress shippingAddress) {
        shippingAddressDAO.save(shippingAddress);
    }

    @Override
    public void deleteById(Long id) {
        shippingAddressDAO.deleteById(id);
    }
}
