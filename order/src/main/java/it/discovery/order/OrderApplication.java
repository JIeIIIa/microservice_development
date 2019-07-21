package it.discovery.order;

import it.discovery.order.domain.Customer;
import it.discovery.order.repository.CustomerRepository;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class OrderApplication {

  private final CustomerRepository customerRepository;

  public static void main(String[] args) {
    SpringApplication.run(OrderApplication.class, args);
  }

  @PostConstruct
  public void setup() {
    Customer customer = new Customer();
    customer.setId(1);
    customer.setName("Sam Newman");
    customer.setEmail("sam.newman@gmail.com");
    customerRepository.save(customer);
  }

}
