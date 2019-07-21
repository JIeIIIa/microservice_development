package it.discovery.order.service;

import it.discovery.event.bus.EventBus;
import it.discovery.order.OrderApplication;
import it.discovery.order.domain.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(OrderApplication.class)
public class OrderServiceTest {
  @MockBean
  private EventBus eventBus;

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
