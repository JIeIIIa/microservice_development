package it.discovery.event;

import lombok.Value;

@Value
public class PendingOrderEvent implements BaseEvent {

  private int orderId;
}
