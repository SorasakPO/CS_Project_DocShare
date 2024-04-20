package ku.cs.csProject.service;

import ku.cs.csProject.entity.Book;
import ku.cs.csProject.entity.User;
import ku.cs.csProject.repository.BookRepository;
import ku.cs.csProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.UUID;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    public void sendSimpleMessage(UUID bookId, Principal principal) {

        Book book = bookRepository.findByBookId(bookId);
        User recipient = userRepository.findByEmail(principal.getName());
        String bookType = book.getBookGiveType().equals("DONATION_BOOK") ? "บริจาค" : "ยืม-คืน";

        String subject = recipient.getFirstName() + " " +recipient.getLastName() + " " + "สนใจเอกสารคุณ";
        String text = "สวัสดี " + book.getOwner().getFirstName() + ",\n\n" +
                "คุณ " + recipient.getFirstName() + " " + recipient.getLastName() + " สนใจเอกสารของคุณ\n\n" +
                "รายละเอียดเอกสาร:\n" +
                "ชื่อ: " + book.getBookName() + "\n" +
                "ประเภท: "  + bookType + "\n\n" +
                "ขอบคุณมากครับ/ค่ะ";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("your-email");
        message.setTo(book.getOwner().getEmail());
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
}
