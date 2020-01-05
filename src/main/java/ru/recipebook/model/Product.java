package ru.recipebook.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;
import ru.recipebook.HasId;
import ru.recipebook.View;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.beans.ConstructorProperties;

@Entity
@Table(name = "products")
public class Product extends AbstractNamedEntity implements HasId {

    @Column(name = "volume", nullable = false)
    @NotNull
    @Range(max = 10000)
    private Double volume;

    @Column(name = "volumeMeasure", nullable = false)
    @NotBlank
    @Size(min = 1, max = 20)
    private String volumeMeasure;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull(groups = View.Persist.class)
    @JsonBackReference
    private Recipe recipe;

    public Product() {
    }

    public Product(String name, Double volume, String volumeMeasure) {
        this(null,name,volume,volumeMeasure);
    }

    public Product(Integer id, String name, Double volume, String volumeMeasure) {
        super(id, name);
        this.volume = volume;
        this.volumeMeasure = volumeMeasure;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public String getVolumeMeasure() {
        return volumeMeasure;
    }

    public void setVolumeMeasure(String volumeMeasure) {
        this.volumeMeasure = volumeMeasure;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name=" + name +
                ", volume=" + volume +
                ", volumeMeasure=" + volumeMeasure +
                '}';
    }
}
