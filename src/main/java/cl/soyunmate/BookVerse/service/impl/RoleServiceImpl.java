package cl.soyunmate.BookVerse.service.impl;

import cl.soyunmate.BookVerse.model.RoleEntity;
import cl.soyunmate.BookVerse.persistence.IRoleDAO;
import cl.soyunmate.BookVerse.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private IRoleDAO roleDAO;

    @Override
    public List<RoleEntity> findAll() {
        return roleDAO.findAll();
    }

    @Override
    public Optional<RoleEntity> findById(Long id) {
        return roleDAO.findById(id);
    }

    @Override
    public void save(RoleEntity role) {
        roleDAO.save(role);
    }

    @Override
    public void deleteById(Long id) {
        roleDAO.deleteById(id);
    }
}
