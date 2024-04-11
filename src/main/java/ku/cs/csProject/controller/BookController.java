package ku.cs.csProject.controller;

import ku.cs.csProject.common.BookGiveType;
import ku.cs.csProject.common.BookStatus;
import ku.cs.csProject.entity.Book;
import ku.cs.csProject.model.BookRequest;
import ku.cs.csProject.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/donation")
    public String getBooksByBookGiveTypeDonation(Model model) {
        List<Book> books = bookService.getBooksByBookGiveTypeAndBookStatus(BookGiveType.DONATION_BOOK, BookStatus.AVAILABLE);
        model.addAttribute("books", books);
        return "books-donation";
    }

    @GetMapping("/lending")
    public String getBookByBookGiveTypeLending(Model model) {
        model.addAttribute("books", bookService.getBooksByBookGiveTypeAndBookStatus(BookGiveType.LENDING_BOOK, BookStatus.AVAILABLE));
        return "books-lending";
    }

    @GetMapping("/add")
    public String getProductForm(Model model) {
        return "book-add";
    }

    @PostMapping("/add")
    public String createBook(@ModelAttribute BookRequest bookRequest, @RequestParam("bookImagePath") MultipartFile bookImagePath, @RequestParam("giveType") String giveType, Principal principal) {
        bookService.createBook(bookRequest, bookImagePath, giveType, principal);
        return "redirect:/books/add";
    }

    @PostMapping("/acceptDonation")
    public String acceptDonation(@RequestParam UUID bookId, Principal principal) {
        bookService.acceptDonation(bookId, principal);
        System.out.println(bookId);
        return "redirect:/books/donation";
    }

    @PostMapping("/acceptLending")
    public String acceptLending(@RequestParam UUID bookId, @RequestParam String bookReturnDate, Principal principal) {
        bookService.acceptLending(bookId, bookReturnDate, principal);
        return "redirect:/books/lending";
    }

    @PostMapping("/returnBook")
    public String returnBook(@RequestParam UUID transactionId) {
        bookService.returnBook(transactionId);
        return "redirect:/transactions/myShelf?type=LENDING_BOOK";
    }

    @GetMapping("/myBook")
    public String getMyBook(Principal principal, Model model){
        model.addAttribute("books", bookService.getMyBook(principal));
        return "book-management";
    }
}
