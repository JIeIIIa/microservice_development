package it.discovery.payment.service;

import it.discovery.event.NotificationEvent;
import it.discovery.event.bus.EventBus;
import it.discovery.order.domain.Order;
import it.discovery.order.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentService {

  private final OrderRepository orderRepository;

  private final PaymentProvider paymentProvider;

  private final EventBus eventBus;

  public void pay(Order order) {
    System.out.println("Charging " + order.getAmount() + " from credit card " + order.getCustomer()
        .getCardNumber());

    paymentProvider.charge(order);

    order.setPayed(true);
    orderRepository.save(order);

    NotificationEvent event = NotificationEvent.builder()
        .email(order.getCustomer().getEmail())
        .recipient(order.getCustomer().getName())
        .title("Order " + order.getId() + " was payed")
        .text("Hi/n. Your order was payed successfully")
        .build();

    eventBus.send(event);
    System.out.println("Charging completed");
  }

  private void notifyPendingOrders() {
    while (true) {
      try {
        orderRepository.findAll()
            .stream()
            .filter(order -> !order.isPayed()).forEach(
            this::sendPendingOrderNotification);
        // Activate each 30 seconds
        Thread.sleep(30 * 60 * 1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  private void sendPendingOrderNotification(Order order) {
    NotificationEvent event = NotificationEvent.builder()
        .email(order.getCustomer().getEmail())
        .recipient(order.getCustomer().getName())
        .title("Don't forget to pay order " + order.getId())
        .text("Please, pay your outstaning order " + order.getId())
        .build();

    eventBus.send(event);
  }
}
