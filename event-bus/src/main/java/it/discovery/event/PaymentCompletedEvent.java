package it.discovery.event;

import lombok.Value;

@Value
public class PaymentCompletedEvent implements BaseEvent {

  private int orderId;
}
