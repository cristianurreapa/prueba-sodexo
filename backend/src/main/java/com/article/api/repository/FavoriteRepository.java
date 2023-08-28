package com.article.api.repository;

import com.article.api.entities.Article;
import com.article.api.entities.Favorite;
import com.article.api.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    Optional<Favorite> findByUserAndArticle(User user, Article article);
    Page<Favorite> findAllByUserAndArticle_TitleContainingIgnoreCaseOrderByCreatedAtDesc(User user, String article_title, Pageable pageable);
    Page<Favorite> findAllByUserAndArticle_TitleContainingIgnoreCaseOrderByCreatedAtAsc(User user, String article_title, Pageable pageable);

    Optional<Favorite> findByUserAndArticle_ApiId(User user, Long article_apiId);

}
