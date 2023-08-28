package com.article.api.service;


import com.article.api.entities.*;
import com.article.api.domain.ArticleDto;
import com.article.api.domain.ArticlesPageable;
import com.article.api.exception.FavoriteException;
import com.article.api.repository.ArticleRepository;
import com.article.api.repository.FavoriteRepository;
import com.article.api.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    private final UserRepository userRepository;

    private final FavoriteRepository favoriteRepository;

    public ArticleDto save(ArticleDto articleDto) {

        var authentication = SecurityContextHolder.getContext().getAuthentication();

        var user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Optional<Article> optionalArticle = this.articleRepository.findByApiId(articleDto.getApiId());

        var favorite = new Favorite();
        favorite.setUser(user);
        favorite.setIsDeleted(false);

        if (optionalArticle.isPresent()) {
            favorite.setArticle(optionalArticle.get());

            Optional<Favorite> optionalFavorite = favoriteRepository.findByUserAndArticle(user, optionalArticle.get());

            if (optionalFavorite.isPresent() && !optionalFavorite.get().getIsDeleted()) {
                throw new FavoriteException("The article is already in your favorites");
            }

        } else {
            Article article = new Article();
            BeanUtils.copyProperties(articleDto, article);
            article = this.articleRepository.save(article);
            favorite.setArticle(article);
        }

        favorite = this.favoriteRepository.save(favorite);
        articleDto.setFavorite(true);
        articleDto.setFavoriteDate(favorite.getCreatedAt());

        return articleDto;
    }

    public ArticlesPageable findAll(Pageable pageable, String title, String order) {

        var user = getUser();

        ArticlesPageable articlesPageable = new ArticlesPageable();

        if (ObjectUtils.isEmpty(title)) {
            title = "";
        }

        Page<Favorite> articles;

        if ("asc".equalsIgnoreCase(order)) {
            articles = this.favoriteRepository.findAllByUserAndArticle_TitleContainingIgnoreCaseOrderByCreatedAtAsc(user, title, pageable);
        } else {
            articles = this.favoriteRepository.findAllByUserAndArticle_TitleContainingIgnoreCaseOrderByCreatedAtDesc(user, title, pageable);
        }

        List<ArticleDto> articleDtos = new ArrayList<>();

        for (Favorite favorite : articles.stream().toList()) {
            ArticleDto articleDto = new ArticleDto();
            articleDto.setFavorite(true);
            articleDto.setTitle(favorite.getArticle().getTitle());
            articleDto.setSummary(favorite.getArticle().getSummary());
            articleDto.setFavoriteDate(favorite.getCreatedAt());
            articleDto.setPublishedAt(favorite.getArticle().getPublishedAt());
            articleDto.setApiId(favorite.getArticle().getApiId());

            articleDtos.add(articleDto);
        }

        articlesPageable.setData(articleDtos);
        articlesPageable.setSizeContent(articles.getTotalElements());
        articlesPageable.setIndex(pageable.getPageNumber());

        return articlesPageable;
    }

    public Articles isFavorite(Articles articles) {

        var user = getUser();
        for (ArticleDto articleDto : articles.getData()) {
            Optional<Favorite> favorite = this.favoriteRepository.findByUserAndArticle_ApiId(user, articleDto.getApiId());
            if (favorite.isPresent()) {
                articleDto.setFavoriteDate(favorite.get().getCreatedAt());
                articleDto.setFavorite(true);
            }
        }

        return articles;
    }

    public Article update(Article article) {
        if (article.getId() == null || !articleRepository.existsById(article.getId())) {
            throw new RuntimeException("The article is not registered.");
        }
        return articleRepository.save(article);
    }

    public ArticleDto findById(Long id) {

        var user = getUser();
        Optional<Favorite> favorite = this.favoriteRepository.findByUserAndArticle_ApiId(user, id);

        if (favorite.isEmpty()) {
            throw new RuntimeException("The article is not found in your favorites");
        }

        ArticleDto articleDto = new ArticleDto();
        articleDto.setApiId(favorite.get().getArticle().getApiId());
        articleDto.setFavorite(true);
        articleDto.setFavoriteDate(favorite.get().getCreatedAt());
        articleDto.setTitle(favorite.get().getArticle().getTitle());
        articleDto.setSummary(favorite.get().getArticle().getSummary());
        articleDto.setPublishedAt(favorite.get().getArticle().getPublishedAt());

        return articleDto;
    }

    private User getUser() {

        var authentication = SecurityContextHolder.getContext().getAuthentication();

        Optional<User> user = this.userRepository.findByEmail(authentication.getName());

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        return user.get();
    }

    public void deleteById(Long id) {

        var user = getUser();
        Optional<Favorite> favorite = this.favoriteRepository.findByUserAndArticle_ApiId(user, id);

        if (favorite.isEmpty()) {
            throw new RuntimeException("The article is not found in your favorites");
        }
        favorite.get().setIsDeleted(true);

        this.favoriteRepository.save(favorite.get());
    }
}
