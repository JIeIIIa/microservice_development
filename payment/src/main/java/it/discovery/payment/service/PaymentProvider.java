package it.discovery.payment.service;

import it.discovery.order.domain.domain.OrderDTO;
import org.springframework.stereotype.Component;

@Component
public class PaymentProvider {

	public void charge(OrderDTO order) {
		if (order.getAmount() > order.getCustomer().getBalance()) {
			throw new RuntimeException("Not enough balance for order " + order.getId());
		}

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//TODO: process payment
//		order.getCustomer().setBalance(order.getCustomer().getBalance() - order.getAmount());

	}

}
