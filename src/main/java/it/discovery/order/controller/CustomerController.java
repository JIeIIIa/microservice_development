package it.discovery.order.controller;

import it.discovery.order.domain.Customer;
import it.discovery.order.repository.CustomerRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
@AllArgsConstructor
public class CustomerController {
  private final CustomerRepository customerRepository;

  @PostMapping
  public void saveCustomer(Customer customer) {
    customerRepository.save(customer);
  }

  @GetMapping
  public List<Customer> findCustomers() {
    return customerRepository.findAll();
  }
}
