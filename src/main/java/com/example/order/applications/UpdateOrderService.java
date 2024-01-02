package com.example.order.applications;

import com.example.order.domains.Order;
import com.example.order.domains.OrderStatus;
import com.example.order.exceptions.OrderNotExist;
import com.example.order.repositories.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateOrderService {

    private final OrderRepository orderRepository;

    public UpdateOrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public String updateOrder(Long orderId, String orderStatus) {

        OrderStatus status = OrderStatus.isInStatus(orderStatus);

        Order order = orderRepository.findById(orderId).orElseThrow(OrderNotExist::new);

        order.changeOrderStatus(status);

        orderRepository.save(order);
        return "success";
    }
}
