package lt.codeacademy.recipetask.converters;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lt.codeacademy.recipetask.dto.CreateIngredientRequest;
import lt.codeacademy.recipetask.dto.GetIngredientResponse;
import lt.codeacademy.recipetask.entities.Ingredient;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access= AccessLevel.PRIVATE)
public final class IngredientConverter {
    public static Ingredient convertCreateIngredientRequestToIngredient(CreateIngredientRequest createIngredientRequest) {
        Ingredient ingredient = null;
        if (createIngredientRequest != null) {
            ingredient = new Ingredient();
            ingredient.setName(createIngredientRequest.getName());
            ingredient.setCategory(createIngredientRequest.getCategory());

        }
        return ingredient;
    }

    public static GetIngredientResponse convertIngredientToGetIngredientResponse(Ingredient ingredient) {
        GetIngredientResponse getIngredientResponse = null;
        if (ingredient != null) {
            getIngredientResponse = new GetIngredientResponse();
            getIngredientResponse.setId(ingredient.getId());
            getIngredientResponse.setName(ingredient.getName());
            getIngredientResponse.setCategory(ingredient.getCategory());
        }
        return getIngredientResponse;
    }

    public static List<GetIngredientResponse> convertIngredientsToGetIngredientResponses(List<Ingredient> ingredients) {
        List<GetIngredientResponse> ingredientResponses = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            ingredientResponses.add(convertIngredientToGetIngredientResponse(ingredient));
        }
        return ingredientResponses;
    }
}
