package cl.soyunmate.BookVerse.service;

import cl.soyunmate.BookVerse.model.RoleEntity;

import java.util.List;
import java.util.Optional;

public interface IRoleService {

    List<RoleEntity> findAll();
    Optional<RoleEntity> findById(Long id);

    void save(RoleEntity role);

    void deleteById(Long id);
}
