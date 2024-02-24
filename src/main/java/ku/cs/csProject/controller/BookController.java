package ku.cs.csProject.controller;

import ku.cs.csProject.model.BookRequest;
import ku.cs.csProject.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

//    @GetMapping
//    public String getAllProducts(Model model) {
//        model.addAttribute("books", bookService.getAllBook());
//        return "book-all";
//    }

    @GetMapping("/add")
    public String getProductForm(Model model) {
        return "book-add";
    }

    @PostMapping("/add")
    public String createProduct(@ModelAttribute BookRequest book,
                                Model model) {
        bookService.createBook(book);
        return "redirect:/books";
    }

//    @PostMapping("/addToCart")
//    public String addToCart(@RequestParam UUID productId, Principal principal) {
//        Member owner = memberService.getByUsername(principal.getName());
//        Product product = productService.getProductById(productId);
//        Cart cart = cartService.getCartByOwner(owner);
//        if (cart == null) {
//            cart = cartService.createCart(owner);
//        }
//
//        cartService.addToCart(cart, product, 1); // 1 is the default quantity
//        return "redirect:/products";
//    }
}
