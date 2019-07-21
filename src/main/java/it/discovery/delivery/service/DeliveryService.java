package it.discovery.delivery.service;

import it.discovery.notification.domain.Notification;
import it.discovery.notification.service.NotificationService;
import it.discovery.order.domain.Order;
import it.discovery.order.repository.OrderRepository;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeliveryService {
	private final OrderRepository orderRepository;
	
	private final NotificationService notificationService;
	
	public void deliver(Order order) {
		order.setDelivered(true);
		order.setDeliveryDate(LocalDateTime.now());
		
		orderRepository.save(order);	
		
		Notification notification = new Notification();
		notification.setEmail(order.getCustomer().getEmail());
		notification.setRecipient(order.getCustomer().getName());
		notification.setTitle("Order " + order.getId() + " is delivered");
		notification.setText("Hi/n. Your order has been delivered");
		
		notificationService.sendNotification(notification);

		System.out.println("Order delivered!");
	}

	@Scheduled(fixedDelay = 3000)
	private void startDeliveries() {
			try {
				orderRepository.findAll().stream().filter(order -> order.isPayed() && !order.isDelivered())
						.forEach(this::deliver);
				// Activate each 30 seconds
				Thread.sleep(30 * 60 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
}
