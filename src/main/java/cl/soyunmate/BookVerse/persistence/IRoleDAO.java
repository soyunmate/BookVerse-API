package cl.soyunmate.BookVerse.persistence;

import cl.soyunmate.BookVerse.model.RoleEntity;

import java.util.List;
import java.util.Optional;

public interface IRoleDAO {
    List<RoleEntity> findAll();
    Optional<RoleEntity> findById(Long id);

    void save(RoleEntity role);

    void deleteById(Long id);
}
