package ku.cs.csProject.service;

import ku.cs.csProject.entity.Book;
import ku.cs.csProject.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.io.IOException;
import java.util.UUID;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Value("${upload-dir}")
    private String uploadDir; // Injecting upload directory path from application.properties

    public String createBook(String bookName, String bookDes, String bookDueDate, MultipartFile bookImagePath) {
        try {
            // Generate a unique filename
            String extension = getFileExtension(bookImagePath.getOriginalFilename());
            String fileName = UUID.randomUUID().toString() + "." + extension;

            // Create upload directory if it doesn't exist
            createUploadDirectoryIfNotExists();

            String filePath = uploadDir + File.separator + fileName;
            File file = new File(uploadDir + fileName);
            bookImagePath.transferTo(file);
            System.out.println("File saved to: " + filePath);

            // Save book data to database
            Book book = new Book(bookName, bookDes, bookDueDate, fileName);
            bookRepository.save(book);

            return "Book added successfully!";
        } catch (IOException e) {
            return "Failed to add book: " + e.getMessage();
        }
    }

    // Method to get file extension
    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex != -1 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1);
        }
        return ""; // Empty string if no extension found
    }

    // Method to create upload directory if it doesn't exist
    private void createUploadDirectoryIfNotExists() throws IOException {
        Path path = Paths.get(uploadDir);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }

}
