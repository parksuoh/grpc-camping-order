package com.example.order.applications;

import com.example.order.applications.grpcclient.GrpcGetUserService;
import com.example.order.domains.Order;
import com.example.order.domains.OrderItem;
import com.example.order.dtos.GetOrderItemDto;
import com.example.order.dtos.GetOrdersResponseDto;
import com.example.order.dtos.GetUserDto;
import com.example.order.exceptions.NameNotExist;
import com.example.order.repositories.OrderItemRepository;
import com.example.order.repositories.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GetOrderListService {


    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    private final GrpcGetUserService grpcGetUserService;

    public GetOrderListService(OrderRepository orderRepository, OrderItemRepository orderItemRepository, GrpcGetUserService grpcGetUserService) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.grpcGetUserService = grpcGetUserService;
    }

    public List<GetOrdersResponseDto> getOrderList(String name) {

        GetUserDto user = grpcGetUserService.getUser(name);

        if (user.id() == 0) {
            throw new NameNotExist();
        }

        List<Order> orders = orderRepository.findByUserIdOrderByIdDesc(user.id());

        return orders
                .stream()
                .map(order -> {
                    List<OrderItem> orderItems = orderItemRepository.findByOrder_Id(order.id());
                    List<GetOrderItemDto> getOrderItemDtos = orderItemToDto(orderItems);

                    return new GetOrdersResponseDto(
                            order.id(),
                            order.totalPrice().asLong(),
                            order.receiverName().toString(),
                            order.address(),
                            order.orderStatus().toString(),
                            getOrderItemDtos);})
                .toList();


    }

    private List<GetOrderItemDto> orderItemToDto(List<OrderItem> orderItems){

        return orderItems
                .stream()
                .map(orderItem -> new GetOrderItemDto(
                        orderItem.id(),
                        orderItem.productName().toString(),
                        orderItem.productPrice().asLong(),
                        orderItem.productFirstOptionName().toString(),
                        orderItem.productFirstOptionPrice().asLong(),
                        orderItem.productSecondOptionName().toString(),
                        orderItem.productSecondOptionPrice().asLong(),
                        orderItem.unitPrice().asLong(),
                        orderItem.quantity(),
                        orderItem.totalPrice().asLong()
                )).toList();
    }

}
