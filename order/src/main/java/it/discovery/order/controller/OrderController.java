package it.discovery.order.controller;

import it.discovery.order.domain.Order;
import it.discovery.order.service.OrderService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/order")
@AllArgsConstructor
public class OrderController {
  private final OrderService orderService;

  @PostMapping
  public int createOrder(int bookId, int number, int price, int customerId) {
    return orderService.createOrder(bookId, price, number, customerId).getId();
  }

  @PutMapping("/{orderId}")
  public void addBook(@PathVariable("orderId") int orderId, int bookId, int price, int number) {
    orderService.addBook(orderId, bookId, price, number);
  }

  @PutMapping("/{orderId}/complete")
  public void completeOrder(@PathVariable("orderId") int orderId) {
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
