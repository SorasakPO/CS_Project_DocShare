package ku.cs.csProject.controller;

import ku.cs.csProject.service.BookService;
import ku.cs.csProject.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private BookService bookService;

    @GetMapping("/allReports")
    public String getAllReports(Model model){
        model.addAttribute("reports", reportService.getAllReports());
        return "report-admin";
    }

    @PostMapping("/deleteReport")
    public String deleteReport(@RequestParam("reportId") UUID reportId) {
        reportService.deleteReport(reportId);
        return "redirect:/reports/allReports";
    }

    @PostMapping("/changeStatus")
    public String changeStatus(@RequestParam("reportId") UUID reportId) {
        reportService.changeStatus(reportId);
        return "redirect:/reports/allReports";
    }
}
