package it.discovery.monolith;

import it.discovery.library.domain.Book;
import it.discovery.library.repository.BookRepository;
import it.discovery.order.domain.Customer;
import it.discovery.order.repository.CustomerRepository;
import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories(value = {"it.discovery"})
@EntityScan(value = {"it.discovery"})
@ComponentScan(value = {"it.discovery"})
@AllArgsConstructor
@EnableScheduling
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