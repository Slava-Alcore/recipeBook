package ru.recipebook.to;

import ru.recipebook.model.Product;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class RecipeTo extends BaseTo {
    private LocalDateTime dateTime;

    private String description;

    private Integer servings;

    private List<Product> productList;

    public RecipeTo(Integer id, LocalDateTime dateTime, String description, Integer servings, List<Product> productList) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.servings = servings;
        this.productList = productList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeTo that = (RecipeTo) o;
        return servings == that.servings &&
                productList.equals(that.productList) &&
                Objects.equals(id, that.id) &&
                Objects.equals(dateTime, that.dateTime) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateTime, description, servings, productList);
    }

    @Override
    public String toString() {
        return "MealTo{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + servings +
                ", excess=" + productList +
                '}';
    }
}
