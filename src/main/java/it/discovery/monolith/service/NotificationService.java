package it.discovery.monolith.service;

import it.discovery.monolith.domain.Notification;
import it.discovery.monolith.repository.NotificationRepository;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
	private NotificationRepository notificationRepository; 

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

}