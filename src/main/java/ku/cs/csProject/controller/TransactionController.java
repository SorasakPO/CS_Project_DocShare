package ku.cs.csProject.controller;


import ku.cs.csProject.common.BookGiveType;
import ku.cs.csProject.entity.Transaction;
import ku.cs.csProject.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/myShelf")
    public String getBooksByMyShelf(@RequestParam(name = "type", defaultValue = "DONATION_BOOK") String type, Principal principal, Model model) {

        List<Transaction> transactions = transactionService.getTransactionByRecipientIdAndBookGiveType(principal, type);
        model.addAttribute("trans", transactions);
        model.addAttribute("type", type);
        return "book-shelf";
    }

    @PostMapping("/cancelBook")
    public String cancelBook(@RequestParam UUID transactionId) {
        String type = transactionService.cancelBook(transactionId);
        return "redirect:/transactions/myShelf?type=" + type;
    }
}
