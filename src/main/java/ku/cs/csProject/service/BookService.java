package ku.cs.csProject.service;

import ku.cs.csProject.common.BookGiveType;
import ku.cs.csProject.common.BookStatus;
import ku.cs.csProject.entity.Book;
import ku.cs.csProject.entity.Transaction;
import ku.cs.csProject.entity.User;
import ku.cs.csProject.model.BookRequest;
import ku.cs.csProject.repository.BookRepository;
import ku.cs.csProject.repository.TransactionRepository;
import ku.cs.csProject.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TransactionRepository transactionRepository;

    public List<Book> getBooksByBookGiveTypeAndBookStatus(BookGiveType bookGiveType, BookStatus bookStatus) {
        return bookRepository.findByBookGiveTypeAndBookStatus(bookGiveType, bookStatus);
    }

    public void createBook(BookRequest request, MultipartFile bookImagePath, String giveType, Principal principal) {

        Book record = modelMapper.map(request, Book.class);
        User user = userRepository.findByEmail(principal.getName());

        // รับวันที่และเวลาปัจจุบัน
        LocalDateTime currentDateTime = LocalDateTime.now();
        // กำหนดรูปแบบของวันที่และเวลา (รวมมิลลิวินาที)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmssSSS_");
        // สร้างชื่อไฟล์ใหม่
        String fileName =  formatter.format(currentDateTime) + StringUtils.cleanPath(bookImagePath.getOriginalFilename());

        // บันทึกไฟล์ลงในที่เก็บไฟล์ (ตัวอย่าง: uploads/)
        try {
            String uploadDir = "src/main/resources/static/uploads/";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
//            try (InputStream inputStream = bookImagePath.getInputStream()) {
//                Path filePath = uploadPath.resolve(fileName);
//                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            try {
                byte[] bytes = bookImagePath.getBytes();
                Path filePath = uploadPath.resolve(fileName);
                Files.write(filePath, bytes);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        record.setBookGiveType(giveType.equals("DONATION") ? BookGiveType.DONATION_BOOK : BookGiveType.LENDING_BOOK);
        record.setBookImagePath(fileName);
        record.setOwner(user);
        record.setBookStatus(BookStatus.AVAILABLE);
        bookRepository.save(record);
    }

    public void acceptDonation(UUID bookId, Principal principal) {

        User recipient = userRepository.findByEmail(principal.getName());
        Book book = bookRepository.findByBookId(bookId);
        book.setBookStatus(BookStatus.DONATED);
        bookRepository.save(book);
        Transaction transaction = new Transaction();
        transaction.setBook(book);
        transaction.setRecipient(recipient);
        transactionRepository.save(transaction);
    }

    public void acceptLending(UUID bookId, Principal principal) {

        User recipient = userRepository.findByEmail(principal.getName());
        Book book = bookRepository.findByBookId(bookId);
        book.setBookStatus(BookStatus.BORROWED);
        bookRepository.save(book);
        Transaction transaction = new Transaction();
        transaction.setBook(book);
        transaction.setRecipient(recipient);
        transactionRepository.save(transaction);
    }

}
