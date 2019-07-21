package it.discovery.order.domain;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private LocalDateTime createdAt;

	@PrePersist
	public void prePersist() {
		createdAt = LocalDateTime.now();
	}

	private String name;
	
	private String address;
	
	private String phone;
	
	private String email;
	
	private String cardNumber;
	
	private double balance;
	
	@OneToMany
	private List<Order> orders;

}
