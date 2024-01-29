package ku.cs.csProject.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class Book {

    @Id
    @GeneratedValue
    private UUID bookId;

    private String bookImagePath;
    private String bookName;
    private String bookDes; //Book Description
    private String bookGiveType;
    private LocalDateTime bookAddDate;
    private LocalDateTime bookRBD; // Book Return Before Date
    private String bookStatus;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner; //owner
}
