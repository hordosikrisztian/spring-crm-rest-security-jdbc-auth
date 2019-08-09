package com.luv2code.springdemo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.springdemo.entity.Customer;
import com.luv2code.springdemo.service.CustomerService;

@RestController
@RequestMapping("/api")
public class CustomerRestController {

	// Autowire the CustomerService.
	@Autowired
	private CustomerService customerService;

	// Add mapping for GET /customers.
	@GetMapping("/customers")
	public List<Customer> getCustomers() {
		return customerService.getCustomers();
	}

	// Add mapping for GET /customers/{customerId}.
	@GetMapping("/customers/{customerId}")
	public Customer getCustomer(@PathVariable int customerId) {
		Customer customer = customerService.getCustomer(customerId);

		if (customer == null) {
			throw new CustomerNotFoundException("Customer id not found - " + customerId);
		}

		return customer;
	}

	// Add mapping for POST /customers - add new customer.
	@PostMapping("/customers")
	public Customer addCustomer(@RequestBody Customer customer) {
		/*
		 * If an id is sent in the JSON we set the id to 0; also, this forces
		 * a save (instead of an update - saveOrUpdate() works this way in
		 * Hibernate).
		 *
		 * 0 -> INSERT
		 * else -> UPDATE
		 */
		customer.setId(0);

		customerService.saveCustomer(customer);

		return customer;
	}

	// Add mapping for PUT /customers - update existing customer.
	@PutMapping("/customers")
	public Customer updateCustomer(@RequestBody Customer customer) {
		customerService.saveCustomer(customer);

		return customer;
	}

	// Add mapping for DELETE /customers/{customerId} - delete customer.
	@DeleteMapping("/customers/{customerId}")
	public String deleteCustomer(@PathVariable int customerId) {
		Customer customer = customerService.getCustomer(customerId);

		// Throw exception if null.
		if (customer == null) {
			throw new CustomerNotFoundException("Customer id not found - " + customerId);
		}

		customerService.deleteCustomer(customerId);

		return "Deleted customer id - " + customerId;
	}

}
