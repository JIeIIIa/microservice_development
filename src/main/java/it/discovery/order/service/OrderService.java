package it.discovery.order.service;

import it.discovery.monolith.service.PaymentService;
import it.discovery.notification.domain.Notification;
import it.discovery.notification.service.NotificationService;
import it.discovery.order.domain.Order;
import it.discovery.order.domain.OrderItem;
import it.discovery.order.repository.CustomerRepository;
import it.discovery.order.repository.OrderRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;

	private final CustomerRepository customerRepository;

	private final NotificationService notificationService;

	private final PaymentService paymentService;

	public void complete(int orderId) {
		orderRepository.findById(orderId).ifPresent(order -> {
			paymentService.pay(order);

			orderRepository.save(order);

			Notification notification = new Notification();
			notification.setEmail(order.getCustomer().getEmail());
			notification.setRecipient(order.getCustomer().getName());
			notification.setTitle("Order " + order.getId() + " is completed");
			notification.setText("Hi/n. Your order has been completed");

			notificationService.sendNotification(notification);
		});
	}

	public void cancel(int orderId) {
		orderRepository.findById(orderId).ifPresent(order -> {
			if (order != null) {
				order.setCancelled(true);
				orderRepository.save(order);

				Notification notification = new Notification();
				notification.setEmail(order.getCustomer().getEmail());
				notification.setRecipient(order.getCustomer().getName());
				notification.setTitle("Order " + order.getId() + " is canceled");
				notification.setText("Hi/n. Your order has been canceled");

				notificationService.sendNotification(notification);

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

}
