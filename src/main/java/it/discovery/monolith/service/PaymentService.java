package it.discovery.monolith.service;

import it.discovery.monolith.domain.Notification;
import it.discovery.monolith.domain.Order;
import it.discovery.monolith.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
	private OrderRepository orderRepository;
	
	private PaymentProvider paymentProvider;
	
	private NotificationService notificationService;
	
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