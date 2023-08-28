package com.article.api.repository;

import com.article.api.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    Optional<Article> findByApiId(Long apiId);


}
