package bootcamp.hibernate_practical.repository;

import bootcamp.hibernate_practical.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAvailableTrue();

    @Query("SELECT b FROM Book b Where b.author = :author")
    List<Book> findByAuthor(@Param("author") String author) ;
    // I know that Spring Data JPA can generate query from method name.
    // I wanted to practice writing @Query

    List<Book> findByPublicationYearGreaterThan(int year);
    List<Book> findByTitleContainingIgnoreCase(String title);
}
