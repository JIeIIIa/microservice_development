package it.discovery.delivery.service;

import it.discovery.delivery.domain.Delivery;
import it.discovery.delivery.repository.DeliveryRepository;
import it.discovery.event.DeliveryCompletedEvent;
import it.discovery.event.NotificationEvent;
import it.discovery.event.bus.EventBus;
import it.discovery.order.domain.domain.OrderDTO;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeliveryService {
	private final DeliveryRepository deliveryRepository;
	
	private EventBus eventBus;

	public void deliver(OrderDTO order) {
//		order.setDelivered(true);
//		order.setDeliveryDate(LocalDateTime.now());
		
		Delivery delivery = Delivery.builder()
				.orderId(order.getId())
				.address(order.getCustomer().getAddress())
				.deliveryDate(LocalDateTime.now())
				.build();
		deliveryRepository.save(delivery);
		eventBus.send(new DeliveryCompletedEvent(order.getId()));

		NotificationEvent notification = NotificationEvent.builder()
				.email(order.getCustomer().getEmail())
				.recipient(order.getCustomer().getName())
				.title("Order " + order.getId() + " is delivered")
				.text("Hi/n. Your order has been delivered")
				.build();
		eventBus.send(notification);

		System.out.println("Order delivered!");
	}

	@Scheduled(fixedDelay = 3000)
	private void startDeliveries() {
			try {
				//FIXME: replace orderRepository
//				orderRepository.findAll().stream().filter(order -> order.isPayed() && !order.isDelivered())
//						.forEach(this::deliver);
				// Activate each 30 seconds
				Thread.sleep(30 * 60 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
}
