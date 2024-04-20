package ku.cs.csProject.repository;

import ku.cs.csProject.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReportRepository extends JpaRepository<Report, UUID> {

    Report findByReportId(UUID reportId);

    List<Report> findAllByBook_BookId(UUID bookID);

}
