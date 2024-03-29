package it.discovery.event;

import it.discovery.order.domain.domain.OrderDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderCompletedEvent implements BaseEvent {

  private OrderDTO orderDTO;
}
