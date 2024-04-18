package ku.cs.csProject.repository;

import ku.cs.csProject.common.BookGiveType;
import ku.cs.csProject.common.TransactionStatus;
import ku.cs.csProject.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    Transaction findByTransactionId(UUID transactionId);

    List<Transaction> findByRecipient_UserID(UUID userID);

    List<Transaction> findByRecipient_UserIDAndBook_BookGiveType(UUID userID, BookGiveType bookGiveType);

    List<Transaction> findByRecipient_UserIDAndAndBook_BookGiveTypeAndAndTransactionStatus(UUID userID, BookGiveType bookGiveType, TransactionStatus transactionStatus);
}
