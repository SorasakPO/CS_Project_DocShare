package ku.cs.csProject.service;

import ku.cs.csProject.common.BookGiveType;
import ku.cs.csProject.entity.Book;
import ku.cs.csProject.entity.Transaction;
import ku.cs.csProject.entity.User;
import ku.cs.csProject.repository.BookRepository;
import ku.cs.csProject.repository.TransactionRepository;
import ku.cs.csProject.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> getTransactionByRecipientIdAndBookGiveType(Principal principal, String type) {
        User recipient = userRepository.findByEmail(principal.getName());
        BookGiveType bookGiveType = BookGiveType.valueOf(type);
        return transactionRepository.findByRecipient_UserIDAndBook_BookGiveType(recipient.getUserID(), bookGiveType);

    }

}
