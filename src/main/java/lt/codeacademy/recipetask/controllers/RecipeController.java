package lt.codeacademy.recipetask.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lt.codeacademy.recipetask.converters.RecipeConverter;
import lt.codeacademy.recipetask.dto.CreateRecipeRequest;
import lt.codeacademy.recipetask.dto.GetRecipeResponse;
import lt.codeacademy.recipetask.entities.Ingredient;
import lt.codeacademy.recipetask.entities.Recipe;
import lt.codeacademy.recipetask.exceptions.ValidationErrorResponse;
import lt.codeacademy.recipetask.services.IngredientService;
import lt.codeacademy.recipetask.services.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/recipes")
public class RecipeController {
    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    @GetMapping
    public ResponseEntity<List<GetRecipeResponse>> getAllRecipes() {
        List<Recipe> recipes = this.recipeService.getAllRecipes();
        if (recipes.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(RecipeConverter.convertRecipeListToGetRecipeResponseList(recipes));
    }

    @PostMapping
    public ResponseEntity<?> createRecipe(@Valid @RequestBody CreateRecipeRequest createRecipeRequest, BindingResult bindingResult){
        log.info("Creating a new recipe");
        log.debug("request body: {}", createRecipeRequest);
        if (bindingResult.hasErrors()){
            log.warn("createRecipe request has errors");
            return ResponseEntity.badRequest().body(new ValidationErrorResponse(bindingResult.getFieldErrors()));
        }

        Recipe recipe = RecipeConverter.convertCreateRecipeRequestToRecipe(createRecipeRequest);
        List<Ingredient> ingredients = this.ingredientService.getIngredientByIds(createRecipeRequest.getIngredientIds());
        if (ingredients.size() != createRecipeRequest.getIngredientIds().size()){
            log.warn("createRecipe not all ingredients exist");
            return ResponseEntity.notFound().build();
        }

        recipe.setIngredients(ingredients);
        Recipe result = this.recipeService.createRecipe(recipe);
        log.info("Recipe created");
        log.debug("created recipe data: {}", recipe);
        return ResponseEntity.status(HttpStatus.CREATED).body(RecipeConverter.convertRecipeToGetRecipeResponse(result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetRecipeResponse> getRecipe(@PathVariable Long id){
        Recipe recipe = this.recipeService.getRecipeById(id);
        if (recipe == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(RecipeConverter.convertRecipeToGetRecipeResponse(recipe));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipeById(@PathVariable Long id){
        if (this.recipeService.deleteRecipeById(id) > 0){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
