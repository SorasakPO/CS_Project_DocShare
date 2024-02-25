package ku.cs.csProject.model;

import lombok.Data;

@Data
public class BookRequest {

    private String bookName;

    private String bookDes;

    private String bookStatus;

    private String bookGiveType;

    private String bookDueDate;

    private String bookImagePath;

}
