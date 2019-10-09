package it.discovery.notification.configuration;

import it.discovery.event.NotificationEvent;
import it.discovery.event.bus.TopicNameCollection;
import it.discovery.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.messaging.handler.annotation.Payload;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
@RequiredArgsConstructor
public class KafkaConfiguration {
  private final NotificationService notificationService;

  @Bean
  public DefaultKafkaConsumerFactory consumerFactory() {
    JsonDeserializer<NotificationEvent> deserializer =
        new JsonDeserializer<>(NotificationEvent.class);
    deserializer.setRemoveTypeHeaders(false);
    deserializer.addTrustedPackages("*");
    deserializer.setUseTypeMapperForKey(true);

    Map<String, Object> configProps = new HashMap<>();
    configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
        "kafka:9092");
    return new DefaultKafkaConsumerFactory(configProps,
        new StringDeserializer(), deserializer);
  }

  @KafkaListener(groupId = "notification", topics = TopicNameCollection.NOTIFICATION)
  public void pay(@Payload NotificationEvent event) {

    notificationService.send(event);
  }

}
