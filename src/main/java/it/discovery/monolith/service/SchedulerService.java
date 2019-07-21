package it.discovery.monolith.service;

import it.discovery.delivery.service.DeliveryService;
import it.discovery.notification.service.NotificationService;
import it.discovery.order.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SchedulerService {
	private final OrderRepository orderRepository;

	private final NotificationService notificationService;

	private final DeliveryService deliveryService;





}
