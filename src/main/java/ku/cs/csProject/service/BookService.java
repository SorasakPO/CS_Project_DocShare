package ku.cs.csProject.service;

import ku.cs.csProject.common.*;
import ku.cs.csProject.common.BookGiveType;
import ku.cs.csProject.common.BookStatus;
import ku.cs.csProject.common.TransactionStatus;
import ku.cs.csProject.common.UserRole;
import ku.cs.csProject.entity.Book;
import ku.cs.csProject.entity.Report;
import ku.cs.csProject.entity.Transaction;
import ku.cs.csProject.entity.User;
import ku.cs.csProject.model.BookRequest;
import ku.cs.csProject.repository.BookRepository;
import ku.cs.csProject.repository.ReportRepository;
import ku.cs.csProject.repository.TransactionRepository;
import ku.cs.csProject.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.LocalDate;
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

    @Autowired
    private ReportRepository reportRepository;

    public List<Book> getBooksByBookGiveTypeAndBookStatus(BookGiveType bookGiveType, BookStatus bookStatus) {
        return bookRepository.findByBookGiveTypeAndBookStatus(bookGiveType, bookStatus);
    }

    public void createBook(BookRequest request, MultipartFile bookImagePath, String giveType, Principal principal) {

        Book record = modelMapper.map(request, Book.class);
        User user = userRepository.findByEmail(principal.getName());

        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmssSSS_");
        String fileName =  formatter.format(currentDateTime) + StringUtils.cleanPath(bookImagePath.getOriginalFilename());
        String uploadDir = "src/main/resources/static/uploads/";

        try {

            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try {
                Path fileNameAndPath = Paths.get(uploadDir, fileName);
                Files.write(fileNameAndPath, bookImagePath.getBytes());
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
        book.setBookStatus(BookStatus.PENDING);
        bookRepository.save(book);

        Transaction transaction = new Transaction();
        transaction.setBook(book);
        transaction.setRecipient(recipient);
        transaction.setBorrowDate(LocalDate.now());
        transaction.setTransactionStatus(TransactionStatus.COMPLETED);
        transactionRepository.save(transaction);
    }

    public void acceptLending(UUID bookId, String bookReturnDate, Principal principal) {

        User recipient = userRepository.findByEmail(principal.getName());
        Book book = bookRepository.findByBookId(bookId);
        book.setBookStatus(BookStatus.PENDING);
        bookRepository.save(book);
        Transaction transaction = new Transaction();
        transaction.setBook(book);
        transaction.setRecipient(recipient);
        transaction.setBorrowDate(LocalDate.now());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(bookReturnDate, formatter);
        transaction.setReturnDate(localDate);
        transaction.setTransactionStatus(TransactionStatus.InPROCESS);
        transactionRepository.save(transaction);
    }

    public String reportBook(UUID bookId, String reportDetail, Principal principal) {
        User reportUser = userRepository.findByEmail(principal.getName());
        Book book = bookRepository.findByBookId(bookId);
        Report report = new Report();
        report.setBook(book);
        report.setReportUser(reportUser);
        report.setReportDetail(reportDetail);
        report.setReportDate(LocalDate.now());
        report.setReportStatus(ReportStatus.PENDING);
        reportRepository.save(report);
        if(book.getBookGiveType().name().equals("DONATION_BOOK")){
            return "donation";
        } else {
            return "lending";
        }
    }

    public void returnBook(UUID transactionId) {

        Transaction transaction = transactionRepository.findByTransactionId(transactionId);
        transaction.setTransactionStatus(TransactionStatus.InPROCESS);
        //transaction อาจจะมีวันที่คืนหนังสือจริงๆ
        transactionRepository.save(transaction);

        Book book = bookRepository.findByBookId(transaction.getBook().getBookId());
        book.setBookStatus(BookStatus.RETURN);
        bookRepository.save(book);
    }

    public List<Book> getMyBook(Principal principal) {
        User owner = userRepository.findByEmail(principal.getName());
        if (owner.getUserRole() == UserRole.ADMIN){
            return bookRepository.findAll();
        }else {
            return bookRepository.findByOwner(owner);
        }
    }

    public Book getBookByBookId(UUID bookId) {
        return bookRepository.findByBookId(bookId);
    }

    public void editBookData(UUID bookId, BookRequest request, String giveType, MultipartFile bookImagePath){
        Book book = bookRepository.findByBookId(bookId);

        book.setBookName(request.getBookName());
        book.setBookDes(request.getBookDes());
        if (giveType.equals("DONATION")){
            book.setBookGiveType(BookGiveType.DONATION_BOOK);
            book.setBookDueDate(null);
        }else {
            book.setBookGiveType(BookGiveType.LENDING_BOOK);
            book.setBookDueDate(request.getBookDueDate());
        }
        if (!bookImagePath.isEmpty()){

            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmssSSS_");
            String fileName =  formatter.format(currentDateTime) + StringUtils.cleanPath(bookImagePath.getOriginalFilename());
            String uploadDir = "src/main/resources/static/uploads/";

            // ลบรูปภาพเก่า (ถ้ามี)
            if (book.getBookImagePath() != null && !book.getBookImagePath().isEmpty()) {
                String oldImagePath = uploadDir + book.getBookImagePath();
                File oldImageFile = new File(oldImagePath);
                if (oldImageFile.exists()) {
                    oldImageFile.delete();
                }
            }

            try {
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                try {
                    Path fileNameAndPath = Paths.get(uploadDir, fileName);
                    Files.write(fileNameAndPath, bookImagePath.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            book.setBookImagePath(fileName);
        }

        bookRepository.save(book);
    }

    public void deleteBook(UUID bookId) {

        List<Transaction> transactionList = transactionRepository.findByBook_BookId(bookId);
        transactionRepository.deleteAll(transactionList);

        List<Report> reportList = reportRepository.findAllByBook_BookId(bookId);
        reportRepository.deleteAll(reportList);

        bookRepository.deleteById(bookId);
    }

    public void confirm(UUID bookId, String confirm){
        Book book = bookRepository.findByBookId(bookId);
        if (book.getBookStatus().name().equals("RETURN")) {
            Transaction transaction = transactionRepository.findByBook_BookIdAndTransactionStatus(bookId, TransactionStatus.InPROCESS);
            transactionRepository.delete(transaction);
            book.setBookStatus(BookStatus.AVAILABLE);
        }else {
            if (confirm.equals("Yes")) {
                book.setBookStatus(book.getBookGiveType().name().equals("DONATION_BOOK") ? BookStatus.DONATED : BookStatus.BORROWED);
            }else {
                book.setBookStatus(BookStatus.AVAILABLE);
                if (book.getBookGiveType().name().equals("DONATION_BOOK")){
                    Transaction transaction = transactionRepository.findByBook_BookIdAndTransactionStatus(bookId, TransactionStatus.COMPLETED);
                    transactionRepository.delete(transaction);
                }else {
                    Transaction transaction = transactionRepository.findByBook_BookIdAndTransactionStatus(bookId, TransactionStatus.InPROCESS);
                    transactionRepository.delete(transaction);
                }
            }
        }
        bookRepository.save(book);
    }

}
