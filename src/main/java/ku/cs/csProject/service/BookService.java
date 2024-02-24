package ku.cs.csProject.service;

import ku.cs.csProject.entity.Book;
import ku.cs.csProject.model.BookRequest;
import ku.cs.csProject.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<Book> getAllBook() {
        return bookRepository.findAll();
    }

    public void createBook(BookRequest request) {
        Book record = modelMapper.map(request, Book.class);
        bookRepository.save(record);
    }

    public Book getBookById(UUID productId) {
        Optional<Book> bookOptional = bookRepository.findById(productId);
        return bookOptional.orElse(null);
    }

}
