package it.discovery.monolith.service;

import it.discovery.event.NotificationEvent;
import it.discovery.monolith.domain.Notification;
import it.discovery.monolith.repository.NotificationRepository;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationService {
	private final NotificationRepository notificationRepository;

	public void sendNotification(Notification notification) {
		System.out.println("Sending notification ... " + notification.toString());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		notificationRepository.save(notification);

		System.out.println("Notification sent");
	}

	@EventListener
	public void send(NotificationEvent event) {
		Notification notification = new Notification();

		notification.setRecipient(event.getRecipient());
		notification.setEmail(event.getEmail());
		notification.setTitle(event.getTitle());
		notification.setText(event.getText());
		notification.setCreated(event.getCreated());

		sendNotification(notification);
	}
}
