package com.example.order.domains;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "product_id")
    private Long productId;

    @Embedded
    @AttributeOverride(name = "text", column = @Column(name = "product_name"))
    private Name productName;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "product_price"))
    private Money productPrice;

    @Column(name = "product_first_option_id")
    private Long productFirstOptionId;

    @Embedded
    private FirstOptionName productFirstOptionName;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "product_first_option_price"))
    private Money productFirstOptionPrice;

    @Column(name = "product_second_option_id")
    private Long productSecondOptionId;

    @Embedded
    private SecondOptionName productSecondOptionName;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "product_second_option_price"))
    private Money productSecondOptionPrice;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "unit_price"))
    private Money unitPrice;

    @Column(name = "quantity")
    private Integer quantity;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "total_price"))
    private Money totalPrice;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updated;

    public OrderItem() {}


    public OrderItem(Order order, Long productId, Name productName, Money productPrice, Long productFirstOptionId, FirstOptionName productFirstOptionName, Money productFirstOptionPrice, Long productSecondOptionId, SecondOptionName productSecondOptionName, Money productSecondOptionPrice, Money unitPrice, Integer quantity, Money totalPrice) {
        this.order = order;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productFirstOptionId = productFirstOptionId;
        this.productFirstOptionName = productFirstOptionName;
        this.productFirstOptionPrice = productFirstOptionPrice;
        this.productSecondOptionId = productSecondOptionId;
        this.productSecondOptionName = productSecondOptionName;
        this.productSecondOptionPrice = productSecondOptionPrice;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public Long id() {
        return id;
    }

    public Name productName() {
        return productName;
    }

    public Money productPrice() {
        return productPrice;
    }

    public Long productFirstOptionId() {
        return productFirstOptionId;
    }

    public FirstOptionName productFirstOptionName() {
        return productFirstOptionName;
    }

    public Money productFirstOptionPrice() {
        return productFirstOptionPrice;
    }

    public Long productSecondOptionId() {
        return productSecondOptionId;
    }

    public SecondOptionName productSecondOptionName() {
        return productSecondOptionName;
    }

    public Money productSecondOptionPrice() {
        return productSecondOptionPrice;
    }

    public Money unitPrice() {
        return unitPrice;
    }

    public Integer quantity() {
        return quantity;
    }

    public Money totalPrice() {
        return totalPrice;
    }
}

