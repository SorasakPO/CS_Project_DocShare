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
    private String bookName;
    private String bookImagePath;
    private String bookDes;
    private String bookStatus;
    private String bookGenre;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner; //owner


}
