package com.example.order.applications.grpcclient;

import com.example.grpc.DeleteCartItemByOrderRequest;
import com.example.grpc.DeleteCartItemByOrderResponse;
import com.example.grpc.DeleteCartItemByOrderServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GrpcDeleteCartItemByOrderService {

    private DeleteCartItemByOrderServiceGrpc.DeleteCartItemByOrderServiceBlockingStub deleteCartItemByOrderServiceBlockingStub;
    private ManagedChannel channel;

    public GrpcDeleteCartItemByOrderService(@Value("${cart.grpc.host}") String grpcHost, @Value("${cart.grpc.port}") int grpcPort) {
        channel = ManagedChannelBuilder.forAddress(grpcHost, grpcPort)
                .usePlaintext()
                .build();
    }

    public boolean deleteCartItem(Long cartItemId) {

        DeleteCartItemByOrderRequest deleteCartItemByOrderRequest = DeleteCartItemByOrderRequest.newBuilder()
                .setCartItemId(cartItemId)
                .build();

        deleteCartItemByOrderServiceBlockingStub = DeleteCartItemByOrderServiceGrpc.newBlockingStub(channel);
        DeleteCartItemByOrderResponse deleteCartItemByOrderResponse = deleteCartItemByOrderServiceBlockingStub.deleteCartItem(deleteCartItemByOrderRequest);

        return deleteCartItemByOrderResponse.getOk();

    }




}
