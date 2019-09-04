package com.apress.ch03.sample01.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.apress.ch03.sample01.model.Item;
import com.apress.ch03.sample01.model.Order;
import com.apress.ch03.sample01.model.PaymentMethod;

@RestController
@RequestMapping(value = "/order")
public class OrderProcessing {

	/**
	 * this method accepts an order id and returns back the status of the order.
	 * 
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value = "/{id}/status", method = RequestMethod.GET)
	public ResponseEntity<?> checkOrderStatus(@PathVariable("id") String orderId) {
		return ResponseEntity.ok("{'status' : 'shipped'}");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getOrder(@PathVariable("id") String orderId) {
		Item book1 = new Item("101", 1);
		Item book2 = new Item("103", 5);
		PaymentMethod myvisa = new PaymentMethod("VISA", "01/22", "John Doe", "201, 1st Street, San Jose, CA");
		Order order = new Order("101021", orderId, myvisa, new Item[] { book1, book2 },
				"201, 1st Street, San Jose, CA");
		return ResponseEntity.ok(order);
	}

}
