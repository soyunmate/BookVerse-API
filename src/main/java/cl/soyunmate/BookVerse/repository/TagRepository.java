package cl.soyunmate.BookVerse.repository;

import cl.soyunmate.BookVerse.model.Tag;
import cl.soyunmate.BookVerse.model.enums.ETag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {
    Optional<Tag> findByName(ETag name);
}
