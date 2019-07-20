package it.discovery.order.service;

import it.discovery.monolith.MonolithApplication;
import it.discovery.order.domain.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(MonolithApplication.class)
public class OrderServiceTest {

  @Autowired
  OrderService orderService;

  @Test
  void createOrder_validParams_orderCreated() {
    int bookId = 1;
    int customerId = 1;
    Order order = orderService.createOrder(bookId, 12345, 1, customerId);
  }

  @Test
  void deliverOrder_validOrder_orderDelivered() {
  }

}
