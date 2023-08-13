package cl.soyunmate.BookVerse.persistence;

import cl.soyunmate.BookVerse.model.ShippingAddress;

import java.util.List;
import java.util.Optional;

public interface IShippingAddressDAO {
    List<ShippingAddress> findAll();

    Optional<ShippingAddress> findById(Long id);

    void save(ShippingAddress shippingAddress);

    void deleteById(Long id);
}
