package com.manosoft.app.commons.dto;

import java.util.Set;

import lombok.Data;

@Data
public class OrderRequestDTO {

    private static class OrderItemRequestDTO{
        
    }

    private String customerId;
    
    private double totalOrderAmount;

    private Set<OrderItemRequestDTO> orderItems;

}
