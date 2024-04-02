package ku.cs.csProject.entity;

import jakarta.persistence.*;
import ku.cs.csProject.common.TransactionStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
public class Transaction {

    @Id
    @GeneratedValue
    private UUID transactionId;
    private LocalDate borrowDate;
    private LocalDate returnDate;

    private TransactionStatus transactionStatus;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User recipient;

}
