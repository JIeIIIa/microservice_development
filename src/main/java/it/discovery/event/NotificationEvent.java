package it.discovery.event;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationEvent implements BaseEvent {

  private String recipient;

  private String email;

  private String title;

  private String text;

  private LocalDateTime created;
}
