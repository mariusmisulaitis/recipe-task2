package lt.codeacademy.recipetask.controllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lt.codeacademy.recipetask.converters.IngredientConverter;
import lt.codeacademy.recipetask.dto.CreateIngredientRequest;
import lt.codeacademy.recipetask.dto.GetIngredientResponse;
import lt.codeacademy.recipetask.entities.Ingredient;
import lt.codeacademy.recipetask.exceptions.ValidationErrorResponse;
import lt.codeacademy.recipetask.services.IngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/ingredients")
public class IngredientController {
    private final IngredientService ingredientService;


    @PreAuthorize("hasAnyRole('CHEF', 'ASSISTANT')")
    @GetMapping
    public ResponseEntity<List<GetIngredientResponse>> getAllIngredients() {
        List<Ingredient> ingredients = this.ingredientService.getAllIngredients();
        if (ingredients.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(IngredientConverter.convertIngredientsToGetIngredientResponses(ingredients));
    }

    @PreAuthorize("hasAnyRole('CHEF', 'ASSISTANT')")
    @PostMapping
    public ResponseEntity<?> createIngredient(@Valid @RequestBody CreateIngredientRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(new ValidationErrorResponse(bindingResult.getFieldErrors()));
        }

        Ingredient ingredient = this.ingredientService.saveIngredient(IngredientConverter.convertCreateIngredientRequestToIngredient(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(IngredientConverter.convertIngredientToGetIngredientResponse(ingredient));
    }

    @PreAuthorize("hasAnyRole('CHEF', 'ASSISTANT')")
    @GetMapping("/{id}")
    public ResponseEntity<GetIngredientResponse> getIngredientById(@PathVariable Long id){
        Ingredient ingredient = this.ingredientService.getIngredientById(id);
        if (ingredient == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(IngredientConverter.convertIngredientToGetIngredientResponse(ingredient));
    }

    @PreAuthorize("hasAnyRole('CHEF')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredientById(@PathVariable Long id){
        if (this.ingredientService.deleteIngredientById(id) > 0){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
