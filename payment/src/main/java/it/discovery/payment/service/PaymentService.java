package it.discovery.payment.service;

import it.discovery.event.NotificationEvent;
import it.discovery.order.domain.domain.OrderDTO;
import it.discovery.payment.domain.Payment;
import it.discovery.payment.repository.PaymentRepository;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class PaymentService {

//  private final OrderRepository orderRepository;

  private final PaymentRepository paymentRepository;
  private final PaymentProvider paymentProvider;

//  private final EventBus eventBus;

  public void pay(OrderDTO order, int paidAmount) {
    System.out.println("Charging " + order.getAmount() + " from credit card " + order.getCustomer()
        .getCardNumber());

    paymentProvider.charge(order);

//    order.setPayed(true);
//    orderRepository.save(order);
    Payment payment = Payment.builder()
        .orderId(order.getId())
        .customerId(order.getCustomer().getId())
        .amount(order.getAmount())
        .paymentDate(LocalDateTime.now())
        .paidAmount(paidAmount)
        .build();
    paymentRepository.save(payment);
    log.debug("Payment was saved: {}", payment);
    NotificationEvent event = NotificationEvent.builder()
        .email(order.getCustomer().getEmail())
        .recipient(order.getCustomer().getName())
        .title("Order " + order.getId() + " was payed")
        .text("Hi/n. Your order was payed successfully")
        .build();

//    eventBus.send(event);
//    eventBus.send(new PaymentCompletedEvent(order.getId()));
    log.info("Charging completed: [orderDTO = {}], [paidAmount = {}]", order, paidAmount );
  }

  private void notifyPendingOrders() {
    while (true) {
      try {
        paymentRepository.findAll()
            .stream()
            .filter(Payment::isPaid).forEach(
            this::sendPendingOrderNotification);
        // Activate each 30 seconds
        Thread.sleep(30 * 60 * 1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  private void sendPendingOrderNotification(Payment payment) {
//    eventBus.send(new PendingOrderEvent(payment.getOrderId()));
  }
}
