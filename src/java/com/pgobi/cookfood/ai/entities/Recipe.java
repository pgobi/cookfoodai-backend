package com.pgobi.cookfood.ai.entities;

import com.pgobi.cookfood.ai.enums.RecipeCategory;
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
public class Recipe implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer recipeId;

    @Column(name = "recipe_name")
    private String recipeName;

    @Enumerated
    @Column(name = "recipe_category")
    private RecipeCategory recipeCategory;

    @Column(name = "instructions")
    private String instructions;

    @Column(name = "description")
    private String description;

    @Column(name = "number_people")
    private String numberPeople;

    @Column(name = "date_created")
    @CreationTimestamp
    private Date dateCreated;



}
