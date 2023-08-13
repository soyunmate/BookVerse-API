package cl.soyunmate.BookVerse.service;

import cl.soyunmate.BookVerse.model.UserEntity;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<UserEntity> findAll();

    Optional<UserEntity> findById(Long id);

    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);

    void save(UserEntity user);

    void deleteById(Long id);
}
