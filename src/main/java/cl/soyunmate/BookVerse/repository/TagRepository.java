package cl.soyunmate.BookVerse.repository;

import cl.soyunmate.BookVerse.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {
}
