package com.example.datapipe.model;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@ToString(exclude = {"documents"})
@EqualsAndHashCode(exclude = {"documents"})
public class Category {

    @Id
    private String id;
    private String name;
    private Instant lastModified;
    private Instant created;
    private String creatorId;
    private String path;

    @ManyToMany(mappedBy = "categories")
    private Set<Document> documents = new HashSet<>();

    public Set<Document> getDocuments() {
        if (documents == null) {
            documents = new HashSet<>();
        }
        return documents;
    }
}
