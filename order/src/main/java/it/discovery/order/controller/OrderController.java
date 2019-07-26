package it.discovery.order.controller;

import it.discovery.order.domain.Order;
import it.discovery.order.service.OrderService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
@Slf4j
public class OrderController {
  private final OrderService orderService;

  @PostMapping
  public int createOrder(
      @RequestParam int bookId,
      @RequestParam int number,
      @RequestParam int price,
      @RequestParam int customerId) {
    log.debug(">> Create order with params: bookId = {}, number = {}, price = {}, customerId = {} ",
        bookId, number, price, customerId);
    return orderService.createOrder(bookId, price, number, customerId).getId();
  }

  @PutMapping("/{orderId}")
  public void addBook(@PathVariable("orderId") int orderId,
      @RequestParam int bookId, @RequestParam int price, @RequestParam int number) {
    log.debug(">> Add book to order: orderId = {}, bookId = {}", orderId, bookId);
    orderService.addBook(orderId, bookId, price, number);
  }

  @PutMapping("/{orderId}/complete")
  public void completeOrder(@PathVariable("orderId") int orderId) {
    log.debug(">> complete order with id = {}", orderId);
    orderService.complete(orderId);
  }

  @DeleteMapping("/{orderId}")
  public void cancel(@PathVariable("orderId") int orderId) {
    orderService.cancel(orderId);
  }

  @GetMapping
  public List<Order> findOrders() {
    return orderService.findOrders();
  }

  @GetMapping("/{orderId}")
  public Order findOrderById(@PathVariable("orderId") int orderId) {
    return orderService.findOrderById(orderId);
  }

}
