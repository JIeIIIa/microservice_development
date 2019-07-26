package it.discovery.payment.configuration;

import it.discovery.event.OrderCompletedEvent;
import it.discovery.event.bus.TopicNameCollection;
import it.discovery.order.domain.domain.OrderDTO;
import it.discovery.payment.service.PaymentService;
import java.util.HashMap;
import java.util.Map;
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

@Configuration
@EnableKafka
@RequiredArgsConstructor
public class KafkaConfiguration {
  private final PaymentService paymentService;

  @Bean
  public DefaultKafkaConsumerFactory consumerFactory() {
    JsonDeserializer<OrderCompletedEvent> deserializer = new JsonDeserializer<>(OrderCompletedEvent.class);
    deserializer.setRemoveTypeHeaders(false);
    deserializer.addTrustedPackages("*");
    deserializer.setUseTypeMapperForKey(true);

    Map<String, Object> configProps = new HashMap<>();
    configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
        "kafka:9092");
    return new DefaultKafkaConsumerFactory(configProps,
        new StringDeserializer(), deserializer);
  }

  @KafkaListener(groupId = "payment", topics = TopicNameCollection.ORDER)
  public void pay(@Payload OrderCompletedEvent event) {
    OrderDTO orderDTO = event.getOrderDTO();
    paymentService.pay(orderDTO, orderDTO.getAmount());
  }

}
