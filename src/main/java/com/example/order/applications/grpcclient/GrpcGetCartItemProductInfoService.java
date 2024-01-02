package com.example.order.applications.grpcclient;

import com.example.grpc.GetCartItemProductInfoRequest;
import com.example.grpc.GetCartItemProductInfoResponse;
import com.example.grpc.GetCartItemProductInfoServiceGrpc;
import com.example.order.dtos.GetCartItemProductInfoDto;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GrpcGetCartItemProductInfoService {

    private GetCartItemProductInfoServiceGrpc.GetCartItemProductInfoServiceBlockingStub getCartItemProductInfoServiceBlockingStub;
    private ManagedChannel channel;

    public GrpcGetCartItemProductInfoService(@Value("${cart.grpc.host}") String grpcHost, @Value("${cart.grpc.port}") int grpcPort) {
        channel = ManagedChannelBuilder.forAddress(grpcHost, grpcPort)
                .usePlaintext()
                .build();
    }



    public GetCartItemProductInfoDto getProductInfo(Long cartItemId) {

        GetCartItemProductInfoRequest getCartItemProductInfoRequest = GetCartItemProductInfoRequest.newBuilder()
                .setCartItemId(cartItemId)
                .build();

        getCartItemProductInfoServiceBlockingStub = GetCartItemProductInfoServiceGrpc.newBlockingStub(channel);
        GetCartItemProductInfoResponse cartItemProductInfo = getCartItemProductInfoServiceBlockingStub.getCartItemProductInfo(getCartItemProductInfoRequest);

        return new GetCartItemProductInfoDto(
                cartItemProductInfo.getProductId(),
                cartItemProductInfo.getFirstOptionId(),
                cartItemProductInfo.getSecondOptionId(),
                cartItemProductInfo.getQuantity());

    }



}
