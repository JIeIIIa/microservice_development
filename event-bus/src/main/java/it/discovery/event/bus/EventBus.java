package it.discovery.event.bus;

import it.discovery.event.BaseEvent;

public interface EventBus {

  void send(BaseEvent event);
}
