package it.discovery.order.service;

import it.discovery.event.NotificationEvent;
import it.discovery.event.OrderCanceledEvent;
import it.discovery.event.OrderCompletedEvent;
import it.discovery.event.PendingOrderEvent;
import it.discovery.event.bus.EventBus;
import it.discovery.order.domain.Order;
import it.discovery.order.domain.OrderItem;
import it.discovery.order.repository.CustomerRepository;
import it.discovery.order.repository.OrderRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;

  private final CustomerRepository customerRepository;

  private final EventBus eventBus;

//	private final PaymentService paymentService;

  public void complete(int orderId) {
    orderRepository.findById(orderId).ifPresent(order -> {
      //TODO: add pay event
//			paymentService.pay(order);

      orderRepository.save(order);

      NotificationEvent event = NotificationEvent.builder()
          .email(order.getCustomer().getEmail())
          .recipient(order.getCustomer().getName())
          .title("Order " + order.getId() + " is completed")
          .text("Hi/n. Your order has been completed")
          .build();
      eventBus.send(event);
      eventBus.send(new OrderCompletedEvent(orderId));
    });
  }

  public void cancel(int orderId) {
    orderRepository.findById(orderId).ifPresent(order -> {
      if (order != null) {
        order.setCancelled(true);
        orderRepository.save(order);

        NotificationEvent event = NotificationEvent.builder()
            .email(order.getCustomer().getEmail())
            .recipient(order.getCustomer().getName())
            .title("Order " + order.getId() + " is canceled")
            .text("Hi/n. Your order has been canceled")
            .build();
        eventBus.send(event);
        eventBus.send(new OrderCanceledEvent(orderId));
      }
    });
  }

  public Order createOrder(int bookId, int price, int number, int customerId) {
    Order order = new Order();
    order.addItem(new OrderItem(bookId, price, number));
    order.setOrderDate(LocalDateTime.now());
    order.setCustomer(customerRepository.getOne(customerId));

    return order;
  }

  public void addBook(int orderId, int bookId, int price, int number) {
    orderRepository.findById(orderId).ifPresent(order -> {
      order.addItem(new OrderItem(bookId, price, number));
      orderRepository.save(order);
    });
  }

  public List<Order> findOrders() {
    return orderRepository.findAll();
  }

  public Order findOrderById(int orderId) {
    return orderRepository.getOne(orderId);
  }

  @EventListener
  public void sendPendingOrderNotification(PendingOrderEvent event) {
    NotificationEvent notificationEvent = orderRepository.findById(event.getOrderId())
        .map(o -> NotificationEvent.builder()
            .email(o.getCustomer().getEmail())
            .recipient(o.getCustomer().getName())
            .title("Don't forget to pay order " + o.getId())
            .text("Please, pay your outstaning order " + o.getId())
            .build()).orElseThrow(() -> new RuntimeException("Order not found"));

    eventBus.send(notificationEvent);
  }
}
