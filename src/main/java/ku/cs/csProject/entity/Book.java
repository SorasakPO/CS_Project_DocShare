package ku.cs.csProject.entity;

import jakarta.persistence.*;
import ku.cs.csProject.common.BookGiveType;
import ku.cs.csProject.common.BookStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class Book {

    @Id
    @GeneratedValue
    private UUID bookId;
    private String bookName;
    private String bookDes;
    private String bookDueDate;
    private String bookImagePath;

    private BookStatus bookStatus;
    private BookGiveType bookGiveType;

    public Book(String bookName, String bookDes, String bookDueDate, String bookImagePath) {
        this.bookName = bookName;
        this.bookDes = bookDes;
        this.bookDueDate = bookDueDate;
        this.bookImagePath = bookImagePath;
    }



    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner; //owner


}
