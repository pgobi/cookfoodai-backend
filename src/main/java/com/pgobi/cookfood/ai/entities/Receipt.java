package com.pgobi.cookfood.ai.entities;

import com.pgobi.cookfood.ai.enums.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "recipes")
public class Receipt implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer recipeId;

    @Column(name = "recipe_name")
    private String name;

    @Enumerated
    @Column(name = "meal_category")
    private Category category;

    @Column(name = "preparation")
    private String preparation;

    @Column(name = "people")
    private String people;

    @Column(name = "date_created")
    @CreationTimestamp
    private Date dateCreated;



}
