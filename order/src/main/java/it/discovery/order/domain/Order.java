package it.discovery.order.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ORDERS")
public class Order {
	@Id
	@GeneratedValue(generator="order_seq")
	@SequenceGenerator(name="order_seq",sequenceName="ORDER_SEQ", allocationSize=1)
	private int id;

	private LocalDateTime createdAt;

	@PrePersist
	public void prePersist() {
		createdAt = LocalDateTime.now();
	}

	@OneToMany(cascade = {CascadeType.ALL})
	private List<OrderItem> items;

	private LocalDateTime orderDate;

	@ManyToOne
	private Customer customer;

	private boolean payed;

	private boolean delivered;

	private boolean cancelled;

	private LocalDateTime deliveryDate;

	private double deliveryPrice;

	public int getAmount() {
		return items.stream().mapToInt(item -> item.getPrice() * item.getNumber()).sum();
	}

	public void addItem(OrderItem item) {
		if (items == null) {
			items = new ArrayList<>();
		}
		items.add(item);
	}

}
