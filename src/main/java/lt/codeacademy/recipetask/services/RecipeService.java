package lt.codeacademy.recipetask.services;

import lombok.RequiredArgsConstructor;
import lt.codeacademy.recipetask.entities.Recipe;
import lt.codeacademy.recipetask.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public Recipe getRecipeById(Long id) {
        return recipeRepository.findById(id).orElse(null);
    }

    public Recipe createRecipe(Recipe recipe) {
        return recipeRepository.saveAndFlush(recipe);
    }

    public long deleteRecipeById(Long id) {
        return recipeRepository.removeById(id);
    }
}
