package com.pgobi.cookfood.ai.repository;

import com.pgobi.cookfood.ai.entities.Receipt;
import com.pgobi.cookfood.ai.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Receipt, Long> {
    List<Receipt> findAllByCategory(Category category);
}
