package it.discovery.notification.service;

import it.discovery.event.NotificationEvent;
import it.discovery.event.bus.TopicNameCollection;
import it.discovery.notification.domain.Notification;
import it.discovery.notification.repository.NotificationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class NotificationService {

  private final NotificationRepository notificationRepository;

  public void sendNotification(Notification notification) {
    log.debug("Sending notification... \n   {}", notification);
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    notificationRepository.save(notification);

    log.info("Notification was sent: {}", notification);
  }

  @KafkaListener(groupId = "notification", topics = TopicNameCollection.NOTIFICATION)
  public void send(NotificationEvent event) {
    Notification notification = Notification.builder()
        .created(event.getCreated())
        .email(event.getEmail())
        .title(event.getTitle())
        .recipient(event.getRecipient())
        .text(event.getText())
        .build();

    sendNotification(notification);
  }
}
