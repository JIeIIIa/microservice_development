package it.discovery.library.controller;

import it.discovery.library.domain.Book;
import it.discovery.library.repository.BookRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("shop/book")
@RequiredArgsConstructor
public class BookController {


//    private String libraryName = "IT-Discovery library";

    private final BookRepository bookRepository;

//    TODO: move to main application
//    @GetMapping("library")
//    public String getLibraryName() {
//      return libraryName;
//    }

    @GetMapping
    public List<Book> getBooks() {
      return bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public Book getBook(@PathVariable("id") Integer id) {
      return bookRepository.getOne(id);
    }

    @PostMapping
    public void saveBook(@RequestBody Book book) {
      bookRepository.save(book);
    }

    @PutMapping("/{id}")
    public void updateBook(@PathVariable Integer id, @RequestBody Book book) {
      bookRepository.save(book);
    }

}
