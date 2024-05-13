package lt.codeacademy.recipetask.repositories;

import jakarta.transaction.Transactional;
import lt.codeacademy.recipetask.entities.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Transactional
    long removeById(Long id);
}
