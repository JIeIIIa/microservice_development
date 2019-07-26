package it.discovery.order.messaging;

import it.discovery.event.NotificationEvent;
import it.discovery.event.OrderCompletedEvent;
import it.discovery.event.bus.TopicNameCollection;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
@AllArgsConstructor
@Slf4j
public class MessageProducer {

  private final KafkaTemplate<String, Object> kafkaTemplate;

  public void sendEvent(OrderCompletedEvent orderCompletedEvent) {
    log.debug(">> send event: {}", orderCompletedEvent);
    ListenableFuture<SendResult<String, Object>> future = kafkaTemplate
        .send(
            TopicNameCollection.ORDER,
            "" + orderCompletedEvent.getOrderDTO().getId(),
            orderCompletedEvent);
    future.addCallback(success -> log.debug("SUCCESS"), err -> log.error("ERROR"));
  }

  public void sendNotification(NotificationEvent notification) {
    kafkaTemplate.send(TopicNameCollection.notification, notification);
  }
}
