package it.discovery.payment.domain;

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
@Table(name = "PAYMENTS")
public class Payment {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  private LocalDateTime createdAt;

  @PrePersist
  public void prePersist() {
    createdAt = LocalDateTime.now();
  }

  private int orderId;

  private int customerId;

  private LocalDateTime paymentDate;

  private int amount;

  private int paidAmount;

  public boolean isPaid() {
    return paidAmount - amount > 0;
  }
}
