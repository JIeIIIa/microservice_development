package it.discovery.delivery.domain;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
@Table(name = "DELIVERIES")
public class Delivery {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  private LocalDateTime createdAt;

  @PrePersist
  public void prePersist() {
    createdAt = LocalDateTime.now();
  }

  private int orderId;

  private String address;

  private LocalDateTime deliveryDate;

}
