package ku.cs.csProject.entity;

import jakarta.persistence.*;
import ku.cs.csProject.common.BookGiveType;
import ku.cs.csProject.common.BookStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
public class Book {

    @Id
    @GeneratedValue
    private UUID bookId;
    private String bookName;
    private String bookDes;
    private LocalDate bookDueDate;
    private String bookImagePath;

    private BookGiveType bookGiveType;
    private BookStatus bookStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner; //owner

}
