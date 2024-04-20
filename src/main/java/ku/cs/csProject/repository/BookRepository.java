package ku.cs.csProject.repository;

import ku.cs.csProject.common.BookGiveType;
import ku.cs.csProject.common.BookStatus;
import ku.cs.csProject.entity.Book;
import ku.cs.csProject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {

    List<Book> findByBookGiveTypeAndBookStatus(BookGiveType bookGiveType, BookStatus bookStatus);
    Book findByBookId(UUID bookId);
    List<Book> findByOwner(User owner);


}
