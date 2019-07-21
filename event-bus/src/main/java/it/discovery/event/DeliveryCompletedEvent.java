package it.discovery.event;

import lombok.Value;

@Value
public class DeliveryCompletedEvent implements BaseEvent {

  private int orderId;
}
