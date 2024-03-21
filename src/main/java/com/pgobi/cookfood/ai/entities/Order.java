package com.pgobi.cookfood.ai.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "orders")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "order_name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "product_detail", columnDefinition = "json")
    private String productDetail;

    @Column(name = "date_created")
    private Date created;
}
