package it.discovery.event.bus;

import it.discovery.event.BaseEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ApplicationEventBus implements EventBus {

  private final ApplicationContext applicationContext;

  @Override
  public void send(BaseEvent event) {
    applicationContext.publishEvent(event);
  }
}
