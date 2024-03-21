package com.pgobi.cookfood.ai.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import java.util.Date;


@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "date_created")
    @CreationTimestamp
    private Date dateCreated;

    @Column(name = "status")
    private String status;

    @Column(name = "total_cost")
    private Double totalCost;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "product_id")
    private Integer product_id;

}
