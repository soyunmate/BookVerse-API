package cl.soyunmate.BookVerse.persistence;

import cl.soyunmate.BookVerse.model.UserEntity;

import java.util.List;
import java.util.Optional;

public interface IUserDAO {
    List<UserEntity> findAll();

    Optional<UserEntity> findById(Long id);

    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);

    void save(UserEntity user);

    void deleteById(Long id);
}
