package com.example.order.applications;

import com.example.order.applications.grpcclient.GrpcDeleteCartItemByOrderService;
import com.example.order.applications.grpcclient.GrpcGetCartItemProductInfoService;
import com.example.order.applications.grpcclient.GrpcGetProductNamesService;
import com.example.order.applications.grpcclient.GrpcGetUserService;
import com.example.order.domains.FirstOptionName;
import com.example.order.domains.Money;
import com.example.order.domains.Name;
import com.example.order.domains.Order;
import com.example.order.domains.OrderItem;
import com.example.order.domains.OrderStatus;
import com.example.order.domains.SecondOptionName;
import com.example.order.dtos.GetCartItemProductInfoDto;
import com.example.order.dtos.GetUserDto;
import com.example.order.dtos.ProductNamesDto;
import com.example.order.exceptions.CartItemNotExist;
import com.example.order.exceptions.DeleteCartItemFail;
import com.example.order.exceptions.NameNotExist;
import com.example.order.exceptions.ProductNotExist;
import com.example.order.repositories.OrderItemRepository;
import com.example.order.repositories.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AddOrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final GrpcGetUserService grpcGetUserService;
    private final GrpcGetProductNamesService grpcGetProductNamesService;
    private final GrpcGetCartItemProductInfoService grpcGetCartItemProductInfoService;
    private final GrpcDeleteCartItemByOrderService grpcDeleteCartItemByOrderService;

    public AddOrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository, GrpcGetUserService grpcGetUserService, GrpcGetProductNamesService grpcGetProductNamesService, GrpcGetCartItemProductInfoService grpcGetCartItemProductInfoService, GrpcDeleteCartItemByOrderService grpcDeleteCartItemByOrderService) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.grpcGetUserService = grpcGetUserService;
        this.grpcGetProductNamesService = grpcGetProductNamesService;
        this.grpcGetCartItemProductInfoService = grpcGetCartItemProductInfoService;
        this.grpcDeleteCartItemByOrderService = grpcDeleteCartItemByOrderService;
    }

    public String addOrder(String name, String receiverName, String address, List<Long> cartItemIds){
        Long cartTotalPrice = 0L;

        GetUserDto user = grpcGetUserService.getUser(name);

        if (user.id() == 0) {
            throw new NameNotExist();
        }

        Order newOrder = new Order(
                user.id(),
                new Money(0L),
                new Name(receiverName),
                address,
                OrderStatus.READY);

        orderRepository.save(newOrder);

        for (Long cartItemId : cartItemIds) {

            GetCartItemProductInfoDto productInfo = grpcGetCartItemProductInfoService.getProductInfo(cartItemId);

            if (productInfo.productId() == 0L){
                throw new CartItemNotExist();
            }

            ProductNamesDto productNames = grpcGetProductNamesService.getProductNames(
                    productInfo.productId(),
                    productInfo.firstOptionId(),
                    productInfo.secondOptionId()
            );

            if (productNames.productName() == "null") {
                throw new ProductNotExist();
            }

            Long unitPrice = productNames.productPrice()
                    + productNames.firstOptionPrice()
                    + productNames.secondOptionPrice();

            Long totalPrice = unitPrice * productInfo.quantity();
            cartTotalPrice += totalPrice;

            OrderItem newOrderItem = new OrderItem(
                    newOrder,
                    productInfo.productId(),
                    new Name(productNames.productName()),
                    new Money(productNames.productPrice()),
                    productInfo.firstOptionId(),
                    new FirstOptionName(productNames.firstName()),
                    new Money(productNames.firstOptionPrice()),
                    productInfo.secondOptionId(),
                    new SecondOptionName(productNames.secondName()),
                    new Money(productNames.secondOptionPrice()),
                    new Money(unitPrice),
                    productInfo.quantity(),
                    new Money(totalPrice)
            );

            orderItemRepository.save(newOrderItem);

            boolean deleteResult = grpcDeleteCartItemByOrderService.deleteCartItem(cartItemId);

            if (!deleteResult){
                throw new DeleteCartItemFail();
            }



        }


        newOrder.changeTotalPrice(new Money(cartTotalPrice));
        orderRepository.save(newOrder);



        return "success";
    }



}
