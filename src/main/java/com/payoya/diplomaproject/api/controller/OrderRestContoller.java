package com.payoya.diplomaproject.api.controller;

import com.payoya.diplomaproject.api.entity.Order;
import com.payoya.diplomaproject.api.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
public class OrderRestContoller {

    private OrderService orderService;

    public OrderRestContoller(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/checkout/{id}")
    public Order saveNewOrder(@PathVariable Long id){
        return orderService.saveOrder(id);
    }

    @GetMapping("/all")
    public List<Order> findAll(){
        return orderService.getAll();
    }

}
