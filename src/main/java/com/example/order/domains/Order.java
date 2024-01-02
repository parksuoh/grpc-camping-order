package com.example.order.domains;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "order_list")
public class Order {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "total_price"))
    private Money totalPrice;

    @Embedded
    @AttributeOverride(name = "text", column = @Column(name = "receiver_name"))
    private Name receiverName;

    @Column(name = "address")
    private String address;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updated;

    @OneToMany(mappedBy = "order")
    List<OrderItem> orderItems = new ArrayList<>();

    public Order() {}

    public Order(Long userId, Money totalPrice, Name receiverName, String address, OrderStatus orderStatus) {
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.receiverName = receiverName;
        this.address = address;
        this.orderStatus = orderStatus;
    }

    public Long id(){return id;}
    public Money totalPrice() {return totalPrice;}
    public Name receiverName() {return receiverName;}
    public String address() {return address;}
    public OrderStatus orderStatus() {return orderStatus;}

    public void changeTotalPrice(Money totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void changeOrderStatus(OrderStatus orderStatus) {
        if(orderStatus.toString().equals("READY")){
            toReady();
        } else if (orderStatus.toString().equals("DELIVERY")) {
            toDelivery();
        } else if (orderStatus.toString().equals("COMPLETE")) {
            toComplete();
        } else if (orderStatus.toString().equals("CANCELED")) {
            toCanceled();
        }

    }

    private void toReady() {
        this.orderStatus = OrderStatus.READY;
    }

    private void toDelivery() {
        this.orderStatus = OrderStatus.DELIVERY;
    }

    private void toComplete() {
        this.orderStatus = OrderStatus.COMPLETE;
    }

    private void toCanceled() {
        this.orderStatus = OrderStatus.CANCELED;
    }




}
