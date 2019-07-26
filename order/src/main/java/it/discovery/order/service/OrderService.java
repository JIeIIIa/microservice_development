package it.discovery.order.service;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import it.discovery.event.NotificationEvent;
import it.discovery.event.OrderCanceledEvent;
import it.discovery.event.OrderCompletedEvent;
import it.discovery.event.PendingOrderEvent;
import it.discovery.event.bus.EventBus;
import it.discovery.order.domain.Order;
import it.discovery.order.domain.OrderItem;
import it.discovery.order.domain.domain.CustomerDTO;
import it.discovery.order.domain.domain.OrderDTO;
import it.discovery.order.messaging.MessageProducer;
import it.discovery.order.repository.CustomerRepository;
import it.discovery.order.repository.OrderRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class OrderService {

  private final OrderRepository orderRepository;

  private final CustomerRepository customerRepository;

  private final EventBus eventBus;

  private final MessageProducer messageProducer;

  private final Mapper mapper = DozerBeanMapperBuilder.buildDefault();

  public void complete(int orderId) {
    log.debug("--> order complete [ orderId = {} ]", orderId);
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
//      eventBus.send(event);
      log.info(" --> order {} completed", orderId);
      OrderDTO orderDTO = //mapper.map(order, OrderDTO.class);
          OrderDTO.builder()
              .amount(order.getAmount())
              .id(order.getId())
              .customer(CustomerDTO.builder()
                  .id(order.getCustomer().getId())
                  .address(order.getCustomer().getAddress())
                  .cardNumber(order.getCustomer().getCardNumber())
                  .balance(order.getCustomer().getBalance())
                  .name(order.getCustomer().getName())
                  .email(order.getCustomer().getEmail())
                  .build())
              .build();

      messageProducer.sendEvent(
          new OrderCompletedEvent(orderDTO));
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

    orderRepository.save(order);
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
