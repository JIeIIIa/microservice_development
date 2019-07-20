package it.discovery.monolith.controller;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import it.discovery.monolith.domain.Book;
import it.discovery.monolith.domain.Customer;
import it.discovery.monolith.domain.Order;
import it.discovery.monolith.repository.BookRepository;
import it.discovery.monolith.repository.CustomerRepository;
import it.discovery.monolith.service.OrderService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ShopController {
	
	private String libraryName = "IT-Discovery library";

	private final BookRepository bookRepository;

	private final CustomerRepository customerRepository;

	private final OrderService orderService;

	@GetMapping("library")
	public String getLibraryName() {
		return libraryName;
	}

	public List<Book> getBooks() {
		return bookRepository.findAll();				
	}

	public Book getBook(int id) {
		return bookRepository.getOne(id);				
	}

	public void saveBook(Book book) {
		bookRepository.save(book);				
	}

	public void updateBook(Book book) {
		bookRepository.save(book);				
	}
	
	public int createOrder(int bookId, int number, int customerId) {
		return orderService.createOrder(bookId, number, customerId).getId();		
	}
	
	public void saveCustomer(Customer customer) {
		customerRepository.save(customer);				
	}

	public List<Customer> findCustomers() {
		return customerRepository.findAll();				
	}

	public void addBook(int orderId, int bookId, int number) {
		orderService.addBook(orderId, bookId, number);
	}
	
	public void completeOrder(int orderId) {
		orderService.complete(orderId);
	}
	
	public void cancel(int orderId) {
		orderService.cancel(orderId);
	}
	
	public List<Order> findOrders() {
		return orderService.findOrders();
	}
	
	public Order findOrderById(int orderId) {
		return orderService.findOrderById(orderId);
	}

}
