package com.article.api.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "summary", nullable = false, length = 1000)
    private String summary;

    @Column(name = "published_at", nullable = false)
    private Date publishedAt;

    @Column(name = "api_id", nullable = false)
    private Long apiId;

    @OneToMany(mappedBy = "article")
    private List<Favorite> favorites;
}
