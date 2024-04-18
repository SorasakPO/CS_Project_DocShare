package ku.cs.csProject.entity;

import jakarta.persistence.*;
import ku.cs.csProject.common.ReportStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
public class Report {

    @Id
    @GeneratedValue
    private UUID reportId;
    private String reportDetail;
    private LocalDate reportDate;

    private ReportStatus reportStatus;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User reportUser;
}
