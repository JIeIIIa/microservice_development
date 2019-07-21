package it.discovery.library;

import it.discovery.library.domain.Book;
import it.discovery.library.repository.BookRepository;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class LibraryApplication {

  private final BookRepository bookRepository;

  public static void main(String[] args) {
    SpringApplication.run(LibraryApplication.class, args);
  }

  @PostConstruct
  public void setup() {
    Book book = new Book();
    book.setName("Thinking in Java");
    book.setPages(1150);
    book.setYear(2006);
    bookRepository.save(book);
  }

}
