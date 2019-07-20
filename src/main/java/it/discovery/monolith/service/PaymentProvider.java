package it.discovery.monolith.service;

import it.discovery.monolith.domain.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class PaymentProvider {

	public void charge(Order order) {
		if (order.getAmount() > order.getCustomer().getBalance()) {
			throw new RuntimeException("Not enough balance for order " + order.getId());
		}

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		order.getCustomer().setBalance(order.getCustomer().getBalance() - order.getAmount());

	}

}
