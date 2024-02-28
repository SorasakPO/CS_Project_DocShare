package ku.cs.csProject.controller;

import ku.cs.csProject.common.BookGiveType;
import ku.cs.csProject.model.BookRequest;
import ku.cs.csProject.repository.BookRepository;
import ku.cs.csProject.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/donation")
    public String getBooksByBookGiveTypeDonation(Model model) {

        model.addAttribute("books", bookService.getBooksByBookGiveType(BookGiveType.DONATION_BOOK));
        return "books-donation";
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
}
