package it.discovery.event;

import lombok.Value;

@Value
public class OrderCanceledEvent implements BaseEvent{
  private int orderId;
}
