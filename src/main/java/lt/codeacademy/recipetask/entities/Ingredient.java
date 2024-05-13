package lt.codeacademy.recipetask.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "ingredients", cascade = CascadeType.ALL)
    private Set<Recipe> recipes = new HashSet<>();

    private String category;  // e.g., "Vegetable", "Meat", "Dairy", etc.

    @Override
    public String toString() {
        return "Ingredient{" +
                "category='" + category + '\'' +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}

