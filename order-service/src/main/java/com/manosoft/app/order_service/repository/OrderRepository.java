package com.manosoft.app.order_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manosoft.app.commons.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order,String> {

}
