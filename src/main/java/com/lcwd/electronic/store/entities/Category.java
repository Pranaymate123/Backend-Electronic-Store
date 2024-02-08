package com.lcwd.electronic.store.entities;


import lombok.*;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories")
@Builder
public class Category {

    @Id
    @Column(name = "id")
    private String categoryId;

    @Column(name = "category_title", length = 60, nullable = false)
    private String title;

    @Column(name = "category_desc", length = 500)
    private String description;

    @Column(length = 1000)
    private String coverImage;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();
}
