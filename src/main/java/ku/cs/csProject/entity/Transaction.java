package ku.cs.csProject.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class Transaction {

    @Id
    @GeneratedValue
    private UUID transactionId;
    private LocalDateTime borrowDate;
    private LocalDateTime returnDate;

    @OneToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User recipient; //ผู้ยืม หรือผู้รับบริจาค
}