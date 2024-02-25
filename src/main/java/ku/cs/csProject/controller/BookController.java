package ku.cs.csProject.controller;

import ku.cs.csProject.entity.Book;
import ku.cs.csProject.model.BookRequest;
import ku.cs.csProject.repository.BookRepository;
import ku.cs.csProject.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/add")
    public String getProductForm(Model model) {
        return "book-add";
    }

    @PostMapping("/add")
    @ResponseBody
    public String createBook(@RequestParam("bookName") String bookName, @RequestParam("bookDes") String bookDes,
                                @RequestParam("bookDueDate") String bookDueDate, @RequestParam("bookImagePath") MultipartFile bookImagePath)
    {
        return bookService.createBook(bookName, bookDes, bookDueDate, bookImagePath);
    }

}
