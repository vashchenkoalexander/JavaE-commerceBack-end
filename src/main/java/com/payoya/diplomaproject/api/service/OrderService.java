package com.payoya.diplomaproject.api.service;

import com.payoya.diplomaproject.api.entity.Order;
import com.payoya.diplomaproject.api.entity.OrderItem;
import com.payoya.diplomaproject.api.entity.ShippingAddress;
import com.payoya.diplomaproject.api.entity.User;
import com.payoya.diplomaproject.api.repository.IOrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {


    private IOrderRepository orderRepository;

    private UserService userService;

    public OrderService(IOrderRepository orderRepository, UserService userService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
    }

    //TODO create order depend on shippingAddress. Allow user to choose from many they shipping addresses in this code.
    public Order saveOrder(Long userId){

        Order order = new Order();
        User user = userService.findUserById(userId);

        if(user.getShoppingCart().getOrderItems().isEmpty()){
            throw new IllegalStateException("You can't checkout 0 item from your cart");
        }

        ShippingAddress shippingAddress = user.getShippingAddressList().get(0);

        List<OrderItem> orderItems = user.getShoppingCart().getOrderItems();
        order.setUser(user);
        order.setOrderItems(new ArrayList<>(orderItems));
        order.getOrderItems().addAll(orderItems);
        order.setOrderItems(new ArrayList<>());
        order.setOrderDate(LocalDateTime.now().withNano(0));
        order.setOrderStatus("Shipping to " + shippingAddress.getCity() + ". With postCode "
                + shippingAddress.getPostcode() + ". Country: " + shippingAddress.getCountry());
        order.setTotalAmount(orderItems.stream().map(item -> item.getItemPrice()).reduce( 0.0, Double::sum));
        for(OrderItem orderItem : orderItems){
            orderItem.setOrder(order);
        }
        user.getShoppingCart().getOrderItems().clear();

        return orderRepository.save(order);
    }

    public List<Order> getAllOrdersByUserId(Long userId){
        return orderRepository.findAllByUserId(userId);
    }

}
