package it.discovery.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEvent implements BaseEvent {

  private String recipient;

  private String email;

  private String title;

  private String text;

  private LocalDateTime created;
}
