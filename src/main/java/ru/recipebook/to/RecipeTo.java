package ru.recipebook.to;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.recipebook.model.Product;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class RecipeTo extends BaseTo {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    private String description;

    private Integer servings;

    @ConstructorProperties({"id", "date", "description", "servings"})
    public RecipeTo(Integer id, Date date, String description, Integer servings) {
        super(id);
        this.date = date;
        this.description = description;
        this.servings = servings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeTo that = (RecipeTo) o;
        return servings == that.servings &&
                Objects.equals(id, that.id) &&
                Objects.equals(date, that.date) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, description, servings);
    }

    @Override
    public String toString() {
        return "RecipeTo{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", calories=" + servings +
                '}';
    }
}
