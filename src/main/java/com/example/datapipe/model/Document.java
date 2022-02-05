package com.example.datapipe.model;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@ToString(exclude = {"categories"})
@EqualsAndHashCode(exclude = {"categories"})
public class Document {

    @Id
    private String id;
    private String title;
    private Instant created;
    private String creatorId;
    private String description;
    private String lastModified;
    private String systemModified;
    @Transient
    private String[] categoryIds;

    @ManyToMany
    @JoinTable
    private Set<Category> categories;

    public void addCategory(Category category) {
        if (!category.getDocuments().contains(this)) {
            category.getDocuments().add(this);
        }
        getCategories().add(category);
    }

    public Set<Category> getCategories() {
        if (categories == null) {
            categories = new HashSet<>();
        }
        return categories;
    }

}
