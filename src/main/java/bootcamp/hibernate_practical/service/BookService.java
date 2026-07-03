package bootcamp.hibernate_practical.service;

import bootcamp.hibernate_practical.dto.BookResponse;
import bootcamp.hibernate_practical.dto.CreateBookRequest;
import bootcamp.hibernate_practical.dto.UpdateBookRequest;
import bootcamp.hibernate_practical.entity.Book;
import bootcamp.hibernate_practical.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookResponse createBook(CreateBookRequest request) {
        Book book = new Book(
                request.getTitle(),
                request.getAuthor(),
                request.getGenre(),
                request.getPublicationYear(),
                true
        );
        Book savedBook = bookRepository.save(book);
        return mapToResponse(savedBook);
    }

    public List<BookResponse> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        List<BookResponse> response = new ArrayList<>();
        for(Book book: books) {
            response.add(mapToResponse(book));
        }
        return response;
    }

    public BookResponse getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No book found by id: " + id));
        return mapToResponse(book);
    }

    public BookResponse updateBook(Long id, UpdateBookRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No book found by id: " + id));

        if (request.getTitle() != null) {
            book.setTitle(request.getTitle());
        }
        if (request.getAuthor() != null) {
            book.setAuthor(request.getAuthor());
        }
        if (request.getGenre() != null) {
            book.setGenre(request.getGenre());
        }
        book.setPublicationYear(request.getPublicationYear());
        book.setAvailable(request.isAvailable());
        // I didn't find the solution without changing request fields to wrapper class for this values

        bookRepository.save(book);
        return mapToResponse(book);
    }

    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No book found by id: " + id));
        bookRepository.delete(book);
    }

    public List<BookResponse> findByAuthor(String author) {
        List<Book> books = bookRepository.findByAuthor(author);
        List<BookResponse> response = new ArrayList<>();
        for (Book book : books) {
            response.add(mapToResponse(book));
        }
        return response;
    }

    public List<BookResponse> findAvailableBooks(){
        List<Book> books = bookRepository.findByAvailableTrue();
        List<BookResponse> response = new ArrayList<>();
        for (Book book : books) {
            response.add(mapToResponse(book));
        }
        return response;
    }

    private BookResponse mapToResponse(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getGenre(),
                book.getPublicationYear(),
                book.isAvailable()
        );
    }
}
