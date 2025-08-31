package com.manosoft.app.order_service;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.manosoft.app.commons.model.Order;
import com.manosoft.app.commons.model.OrderItem;
import com.manosoft.app.order_service.service.OrderService;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@AllArgsConstructor
@EntityScan(basePackages = "com.manosoft.app.commons.model")
public class OrderServiceApplication implements ApplicationRunner{ 

	private final OrderService orderService;

    public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) {

		Order order = new Order();
		order.setCustomerId(1L);
		order.setOrderCreationDT(LocalDateTime.now());
		order.setTotalOrderAmount(1000);

		OrderItem item1 = new OrderItem();
		item1.setPrice(100);
        item1.setQuantity(5);
        item1.setProductCode("PRD001");
        item1.setOrder(order);

        OrderItem item2 = new OrderItem();
        item2.setPrice(50);
        item2.setQuantity(10);
        item2.setProductCode("PRD002");
        item2.setOrder(order);

		order.setOrderItems(Set.of(item1,item2));
		
		orderService.newOrderRequest(order);
	}

}
