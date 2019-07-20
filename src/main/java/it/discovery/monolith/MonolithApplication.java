package it.discovery.monolith;

import javax.annotation.PostConstruct;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import it.discovery.monolith.domain.Book;
import it.discovery.monolith.domain.Customer;
import it.discovery.monolith.repository.BookRepository;
import it.discovery.monolith.repository.CustomerRepository;

@SpringBootApplication
@EnableJpaRepositories("it.discovery.monolith.repository")
@AllArgsConstructor
public class MonolithApplication {
	public static void main(String[] args) {
		SpringApplication.run(MonolithApplication.class, args);
	}

	private final BookRepository bookRepository;
	
	private final CustomerRepository customerRepository;
	
	@PostConstruct
	public void setup() {
		Book book = new Book();
		book.setName("Thinking in Java");
		book.setPages(1150);
		book.setYear(2006);
		bookRepository.save(book);
		
		Customer customer = new Customer();
		customer.setId(1);
		customer.setName("Sam Newman");
		customer.setEmail("sam.newman@gmail.com");
		customerRepository.save(customer);
	}
}