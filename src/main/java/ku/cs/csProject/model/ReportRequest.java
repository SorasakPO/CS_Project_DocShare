package ku.cs.csProject.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReportRequest {

    private String reportDetail;

    private String reportUser;

    private LocalDate reportDate;

}
