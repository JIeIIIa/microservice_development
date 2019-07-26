package it.discovery.order;

import it.discovery.order.domain.Customer;
import it.discovery.order.repository.CustomerRepository;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@SpringBootApplication
@EnableJpaRepositories("it.discovery.order.repository")
@ComponentScan("it.discovery")
@EnableKafka
@RequiredArgsConstructor
public class OrderApplication {

  private final CustomerRepository customerRepository;

  public static void main(String[] args) {
    SpringApplication.run(OrderApplication.class, args);
  }

  @PostConstruct
  public void setup() {
    Customer customer = new Customer();
    customer.setId(1);
    customer.setName("Sam Newman");
    customer.setEmail("sam.newman@gmail.com");
    customer.setAddress("Some address");
    customer.setCardNumber("#1234567");
    customer.setBalance(77767);
    customer.setPhone("+38 (123) 765-43-21");

    customerRepository.save(customer);
  }

  @Bean
  public DefaultKafkaProducerFactory producerFactory() {
    JsonSerializer jsonSerializer = new JsonSerializer();

    Map<String, Object> configProps = new HashMap<>();
    configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
        "kafka:9092");
    return new DefaultKafkaProducerFactory(configProps,
        new StringSerializer(), jsonSerializer);
  }
}
