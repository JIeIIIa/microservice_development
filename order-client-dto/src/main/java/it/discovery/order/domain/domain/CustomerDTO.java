package it.discovery.order.domain.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
	private int id;

	private String name;
	
	private String address;
	
	private String phone;
	
	private String email;
	
	private String cardNumber;
	
	private double balance;
	
}
