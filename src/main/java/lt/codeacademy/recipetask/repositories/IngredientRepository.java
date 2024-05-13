package lt.codeacademy.recipetask.repositories;

import jakarta.transaction.Transactional;
import lt.codeacademy.recipetask.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    @Transactional
    long removeById(Long id);
}
