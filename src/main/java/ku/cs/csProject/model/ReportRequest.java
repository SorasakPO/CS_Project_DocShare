package ku.cs.csProject.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class ReportRequest {

    private String reportDetail;

    private String reportUser;

    private LocalDate reportDate;

}
