package it.discovery.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.discovery.order.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
