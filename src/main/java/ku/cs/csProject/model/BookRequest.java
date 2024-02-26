package ku.cs.csProject.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BookRequest {

    private String bookName;

    private String bookDes;

    private String bookStatus;

    private String bookGiveType;

    private String bookDueDate;

    private MultipartFile bookImagePath;

}
