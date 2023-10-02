package com.payoya.diplomaproject.api.service;

import com.payoya.diplomaproject.api.entity.Order;
import com.payoya.diplomaproject.api.entity.OrderItem;
import com.payoya.diplomaproject.api.entity.User;
import com.payoya.diplomaproject.api.repository.IOrderRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {


    private IOrderRepository orderRepository;

    @Autowired
    private UserService userService;

    public OrderService(IOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order saveOrder(Long userId){

        Order order = new Order();
        User user = userService.findUserById(userId);
        List<OrderItem> orderItems = user.getShoppingCart().getOrderItems();
        order.setUser(user);
        order.setOrderItems(new ArrayList<>(orderItems));
        order.getOrderItems().addAll(orderItems);
        order.setOrderItems(new ArrayList<>());
        order.setOrderDate(LocalDateTime.now().withNano(0));
        order.setOrderStatus("Shipping");
        order.setTotalAmount(orderItems.stream().map(item -> item.getItemPrice()).reduce( 0.0, Double::sum));
        for(OrderItem orderItem : orderItems){
            orderItem.setOrder(order);
        }
        user.getShoppingCart().getOrderItems().clear();

        //user.setOrders(user.getOrders().add(order));
        return orderRepository.save(order);
    }

    public List<Order> getAll(){
        return orderRepository.findAll();
    }

}
