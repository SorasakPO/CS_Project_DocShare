package ku.cs.csProject.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class BookRequest {

    private String bookName;

    private String bookDes;

    private String bookStatus;

    private String bookGiveType;

    private LocalDate bookDueDate;

    private MultipartFile bookImagePath;

}
