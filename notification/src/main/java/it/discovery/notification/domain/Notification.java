package it.discovery.notification.domain;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table
public class Notification {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private LocalDateTime createdAt;

	@PrePersist
	public void prePersist() {
		createdAt = LocalDateTime.now();
	}

	private String recipient;

	private String email;

	private String title;

	private String text;

	private LocalDateTime created = LocalDateTime.now();
}
