package it.discovery.monolith.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import it.discovery.monolith.MonolithApplication;
import it.discovery.monolith.domain.Order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringJUnitConfig(MonolithApplication.class)
public class OrderServiceTest {
	
	@Autowired
	OrderService orderService;

    @Test
    void createOrder_validParams_orderCreated() {
    	int bookId = 1;
    	int customerId = 1;
    	Order order = orderService.createOrder(bookId, 1, customerId);
    	assertNotNull(order);
    	assertEquals(customerId, order.getCustomer().getId());
    	assertEquals(1, order.getItems().size());
    	assertEquals(bookId, order.getItems().get(0).getBook().getId());
    }
    
    @Test
    void deliverOrder_validOrder_orderDelivered() {
    }

}