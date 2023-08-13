package cl.soyunmate.BookVerse.repository;

import cl.soyunmate.BookVerse.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
