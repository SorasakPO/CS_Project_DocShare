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
    private String bookImagePath;
    private String bookDes;
    private String bookDueDate;

    private BookStatus bookStatus;
    private BookGiveType bookGiveType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner; //owner


}
