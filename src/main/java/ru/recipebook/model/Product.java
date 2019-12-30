package ru.recipebook.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "products")
public class Product extends AbstractNamedEntity {
    private static final long serialVersionUID = 1L;

    @Column(name = "volume", nullable = false)
    @NotNull
    @Range(min = 10,max = 10000)
    private Double volume;

    @Column(name = "volumeMeasure", nullable = false)
    @NotBlank
    @Size(min = 2, max = 20)
    private String volumeMeasure;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
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
}
