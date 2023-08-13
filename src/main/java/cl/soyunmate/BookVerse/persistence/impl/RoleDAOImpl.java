package cl.soyunmate.BookVerse.persistence.impl;

import cl.soyunmate.BookVerse.model.RoleEntity;
import cl.soyunmate.BookVerse.persistence.IRoleDAO;
import cl.soyunmate.BookVerse.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RoleDAOImpl implements IRoleDAO {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<RoleEntity> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<RoleEntity> findById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public void save(RoleEntity role) {
        roleRepository.save(role);
    }

    @Override
    public void deleteById(Long id) {
        roleRepository.deleteById(id);
    }
}
