package cl.soyunmate.BookVerse.repository;

import cl.soyunmate.BookVerse.model.Author;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

}
