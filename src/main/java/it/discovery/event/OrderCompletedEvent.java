package it.discovery.event;

import lombok.Value;

@Value
public class OrderCompletedEvent implements BaseEvent {

  private int orderId;
}
