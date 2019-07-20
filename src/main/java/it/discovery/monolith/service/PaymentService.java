package it.discovery.monolith.service;

import it.discovery.monolith.domain.Notification;
import it.discovery.order.domain.Order;
import it.discovery.order.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentService {
	private final OrderRepository orderRepository;
	
	private final PaymentProvider paymentProvider;
	
	private final NotificationService notificationService;
	
	public void pay(Order order) {
		System.out.println("Charging " + order.getAmount() + " from credit card " + order.getCustomer().getCardNumber());
		
		paymentProvider.charge(order);
		
		order.setPayed(true);
		orderRepository.save(order);
		
		Notification notification = new Notification();
		notification.setEmail(order.getCustomer().getEmail());
		notification.setRecipient(order.getCustomer().getName());
		notification.setTitle("Order " + order.getId() + " was payed");
		notification.setText("Hi/n. Your order was payed successfully");

		notificationService.sendNotification(notification);
		System.out.println("Charging completed");
	}

}
