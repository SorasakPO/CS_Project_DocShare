package ku.cs.csProject.service;

import ku.cs.csProject.common.ReportStatus;
import ku.cs.csProject.common.UserRole;
import ku.cs.csProject.entity.Book;
import ku.cs.csProject.entity.Report;
import ku.cs.csProject.entity.User;
import ku.cs.csProject.model.ReportRequest;
import ku.cs.csProject.repository.BookRepository;
import ku.cs.csProject.repository.ReportRepository;
import ku.cs.csProject.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ReportService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    public void deleteReport(UUID reportId) {
        reportRepository.deleteById(reportId);
    }

    public void changeStatus(UUID reportId) {
        Report report = reportRepository.findByReportId(reportId);
        if(report.getReportStatus().name().equals("PENDING")){
            report.setReportStatus(ReportStatus.COMPLETED);
            reportRepository.save(report);
        } else {
            report.setReportStatus(ReportStatus.PENDING);
            reportRepository.save(report);
        }
    }

}
