package ru.recipebook.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;
import ru.recipebook.View;
import ru.recipebook.util.DateTimeUtil;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "recipes")
public class Recipe extends AbstractIdEntity {
    @Column(name = "date_time", nullable = false)
    @NotNull
    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN)
    private LocalDateTime dateTime;

    @Column(name = "description", nullable = false)
    @NotBlank
    @Size(min = 10, max = 120)
    private String description;

    @Column(name = "servings", nullable = false)
    @NotNull
    @Range(max = 16)
    private Integer servings;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recipe", cascade=CascadeType.MERGE)
    @OrderBy("name")
    @NotEmpty
    private List<Product> productList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull(groups = View.Persist.class)
    @JsonBackReference
    private User user;

    public Recipe() {
    }

    public Recipe(LocalDateTime dateTime, String description, Integer servings) {
        this(null,dateTime,description,servings);
    }

    public Recipe(Integer id, LocalDateTime dateTime, String description, Integer servings) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.servings = servings;
    }

    public Recipe(Integer id, LocalDateTime dateTime, String description, Integer servings,List<Product> products) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.servings = servings;
        this.setProductList(products);
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getServings() {
        return servings;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
