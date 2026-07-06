package bootcamp.hibernate_practical.service;

import bootcamp.hibernate_practical.dto.BookResponse;
import bootcamp.hibernate_practical.dto.CreateBookRequest;
import bootcamp.hibernate_practical.dto.UpdateBookRequest;
import bootcamp.hibernate_practical.entity.Book;
import bootcamp.hibernate_practical.exception.BookNotFoundException;
import bootcamp.hibernate_practical.repository.BookRepository;
import org.springframework.stereotype.Service;

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
        return bookRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    public BookResponse getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("No book found by id: " + id));
        return mapToResponse(book);
    }

    public BookResponse updateBook(Long id, UpdateBookRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("No book found by id: " + id));

        if (request.getTitle() != null) {
            if (request.getTitle().isBlank()) {
                throw new IllegalArgumentException("title must not be blank");
            }
            book.setTitle(request.getTitle());
        }
        if (request.getAuthor() != null) {
            if (request.getAuthor().isBlank()) {
                throw new IllegalArgumentException("author must not be blank");
            }
            book.setAuthor(request.getAuthor());
        }
        if (request.getGenre() != null) {
            if (request.getGenre().isBlank()) {
                throw new IllegalArgumentException("genre must not be blank");
            }
            book.setGenre(request.getGenre());
        }
        if (request.getPublicationYear() != 0) {
            if (request.getPublicationYear() < 0) {
                throw new IllegalArgumentException("publicationYear must be positive");
            }
            book.setPublicationYear(request.getPublicationYear());
        }
        book.setAvailable(request.isAvailable());

        bookRepository.save(book);
        return mapToResponse(book);
    }

    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("No book found by id: " + id));
        bookRepository.delete(book);
    }

    public List<BookResponse> findByAuthor(String author) {
        return bookRepository.findByAuthor(author).stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<BookResponse> findAvailableBooks() {
        return bookRepository.findByAvailableTrue().stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<BookResponse> findByPublicationYearAfter(int year) {
        return bookRepository.findByPublicationYearGreaterThan(year).stream()
                .map(this::mapToResponse)
                .toList();
    }

    public long countBooks() {
        return bookRepository.count();
    }

    public List<BookResponse> findByTitleContaining(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(this::mapToResponse)
                .toList();
    }

    public BookResponse borrowBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("No book found by id: " + id));
        if (!book.isAvailable()) {
            throw new IllegalStateException("Book is already borrowed: " + id);
        }
        book.setAvailable(false);
        bookRepository.save(book);
        return mapToResponse(book);
    }

    public BookResponse returnBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("No book found by id: " + id));
        if (book.isAvailable()) {
            throw new IllegalStateException("Book is not borrowed: " + id);
        }
        book.setAvailable(true);
        bookRepository.save(book);
        return mapToResponse(book);
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