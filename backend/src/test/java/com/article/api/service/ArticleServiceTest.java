package com.article.api.service;

import com.article.api.domain.ArticleDto;
import com.article.api.domain.ArticlesPageable;
import com.article.api.entities.Article;
import com.article.api.entities.Articles;
import com.article.api.entities.Favorite;
import com.article.api.entities.User;
import com.article.api.repository.ArticleRepository;
import com.article.api.repository.FavoriteRepository;
import com.article.api.security.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@AllArgsConstructor
class ArticleServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private FavoriteRepository favoriteRepository;

    @InjectMocks
    private ArticleService articleService;

    private Authentication authentication;

    private SecurityContext securityContext;

    @BeforeEach
    void setUp() {
        this.authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("test@email.com");
        this.securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void save() {
        ArticleDto articleDto = new ArticleDto();
        articleDto.setApiId(123123L);

        User user = new User();
        user.setEmail(authentication.getName());

        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        when(articleRepository.findByApiId(any())).thenReturn(Optional.empty());

        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setIsDeleted(false);
        favorite.setCreatedAt(new Date());

        Article article = new Article();
        BeanUtils.copyProperties(articleDto, article);

        when(articleRepository.save(any())).thenReturn(article);
        when(favoriteRepository.save(any())).thenReturn(favorite);

        ArticleDto saved = articleService.save(articleDto);

        assertNotNull(saved);
        assertTrue(saved.isFavorite());
        assertNotNull(saved.getFavoriteDate());
    }

    @Test
    public void saveArticleAlreadyExists() {
        ArticleDto articleDto = new ArticleDto();
        articleDto.setApiId(123123L);

        User user = new User();
        user.setEmail(authentication.getName());

        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));

        Article existingArticle = new Article();
        existingArticle.setApiId(articleDto.getApiId());

        when(articleRepository.findByApiId(any())).thenReturn(Optional.of(existingArticle));

        Favorite existingFavorite = new Favorite();
        existingFavorite.setUser(user);
        existingFavorite.setArticle(existingArticle);
        existingFavorite.setIsDeleted(false);

        when(favoriteRepository.findByUserAndArticle(any(), any())).thenReturn(Optional.of(existingFavorite));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            articleService.save(articleDto);
        });

        assertEquals("The article is already in your favorites", exception.getMessage());
    }

    @Test
    public void saveArticleAlreadyExistsButNotFavorite() {
        ArticleDto articleDto = new ArticleDto();
        articleDto.setApiId(123123L);

        User user = new User();
        user.setEmail(authentication.getName());
        Article existingArticle = new Article();
        existingArticle.setApiId(articleDto.getApiId());

        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setIsDeleted(false);
        favorite.setArticle(existingArticle);
        favorite.setCreatedAt(new Date());

        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        when(articleRepository.findByApiId(any())).thenReturn(Optional.of(existingArticle));
        when(favoriteRepository.findByUserAndArticle(any(), any())).thenReturn(Optional.empty());
        when(favoriteRepository.save(any())).thenReturn(favorite);

        ArticleDto saved = articleService.save(articleDto);

        assertNotNull(saved);
        assertTrue(saved.isFavorite());
        assertNotNull(saved.getFavoriteDate());
    }


    @Test
    public void findAllAsc() {

        int pageNumber = 0;
        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        String title = "testTitle";
        String order = "asc";

        User user = new User();

        Favorite favorite = new Favorite();
        Article article = new Article();
        article.setTitle("testTitle");
        article.setSummary("testSummary");
        article.setPublishedAt(new Date());
        article.setApiId(23213L);
        favorite.setArticle(article);
        favorite.setCreatedAt(new Date());

        Page<Favorite> page = new PageImpl<>(List.of(favorite));

        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        when(favoriteRepository.findAllByUserAndArticle_TitleContainingIgnoreCaseOrderByCreatedAtAsc(any(), any(), any())).thenReturn(page);

        ArticlesPageable articlesPageable = articleService.findAll(pageable, title, order);

        assertNotNull(articlesPageable);
        assertEquals(1, articlesPageable.getSizeContent());
        assertEquals(pageNumber, articlesPageable.getIndex());
        assertEquals(1, articlesPageable.getData().size());
        ArticleDto articleDto = articlesPageable.getData().get(0);
        assertTrue(articleDto.isFavorite());
        assertEquals(article.getTitle(), articleDto.getTitle());
        assertEquals(article.getSummary(), articleDto.getSummary());
        assertEquals(article.getPublishedAt(), articleDto.getPublishedAt());
        assertEquals(article.getApiId(), articleDto.getApiId());
        assertEquals(favorite.getCreatedAt(), articleDto.getFavoriteDate());
    }

    @Test
    public void findAllDesc() {

        int pageNumber = 0;
        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        String title = "testTitle";
        String order = "desc";

        User user = new User();

        Favorite favorite = new Favorite();
        Article article = new Article();
        article.setTitle("testTitle");
        article.setSummary("testSummary");
        article.setPublishedAt(new Date());
        article.setApiId(23213L);
        favorite.setArticle(article);
        favorite.setCreatedAt(new Date());

        Page<Favorite> page = new PageImpl<>(List.of(favorite));

        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        when(favoriteRepository.findAllByUserAndArticle_TitleContainingIgnoreCaseOrderByCreatedAtDesc(any(), any(), any())).thenReturn(page);

        ArticlesPageable articlesPageable = articleService.findAll(pageable, title, order);

        assertNotNull(articlesPageable);
        assertEquals(1, articlesPageable.getSizeContent());
        assertEquals(pageNumber, articlesPageable.getIndex());
        assertEquals(1, articlesPageable.getData().size());
        ArticleDto articleDto = articlesPageable.getData().get(0);
        assertTrue(articleDto.isFavorite());
        assertEquals(article.getTitle(), articleDto.getTitle());
        assertEquals(article.getSummary(), articleDto.getSummary());
        assertEquals(article.getPublishedAt(), articleDto.getPublishedAt());
        assertEquals(article.getApiId(), articleDto.getApiId());
        assertEquals(favorite.getCreatedAt(), articleDto.getFavoriteDate());
    }

    @Test
    public void IsFavorite() {
        User user = new User();

        ArticleDto articleDto = new ArticleDto();
        articleDto.setApiId(231424L);

        Articles articles = new Articles();
        articles.setData(List.of(articleDto));

        Favorite favorite = new Favorite();
        favorite.setCreatedAt(new Date());

        when(favoriteRepository.findByUserAndArticle_ApiId(any(), any())).thenReturn(Optional.of(favorite));
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        Articles result = articleService.isFavorite(articles);

        assertNotNull(result);
        assertEquals(1, result.getData().size());
        ArticleDto resultArticleDto = result.getData().get(0);
        assertEquals(articleDto.getApiId(), resultArticleDto.getApiId());
        assertTrue(resultArticleDto.isFavorite());
        assertEquals(favorite.getCreatedAt(), resultArticleDto.getFavoriteDate());
    }

    @Test
    public void updateArticleExists() {
        Long id = 1L;
        Article article = new Article();
        article.setId(id);

        when(articleRepository.existsById(any())).thenReturn(true);
        when(articleRepository.save(any())).thenReturn(article);

        Article updated = articleService.update(article);

        assertNotNull(updated);
        assertEquals(id, updated.getId());
    }

    @Test
    public void updateArticleDoesNotExist() {
        Long id = 1L;
        Article article = new Article();
        article.setId(id);

        when(articleRepository.existsById(any())).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            articleService.update(article);
        });

        assertEquals("The article is not registered.", exception.getMessage());
    }

    @Test
    public void findByIdFavoriteExists() {
        Long id = 1L;
        User user = new User();

        Favorite favorite = new Favorite();
        Article article = new Article();
        article.setApiId(1213L);
        article.setTitle("testTitle");
        article.setSummary("testSummary");
        article.setPublishedAt(new Date());
        favorite.setArticle(article);
        favorite.setCreatedAt(new Date());

        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        when(favoriteRepository.findByUserAndArticle_ApiId(any(), any())).thenReturn(Optional.of(favorite));

        ArticleDto result = articleService.findById(id);

        assertNotNull(result);
        assertEquals(article.getApiId(), result.getApiId());
        assertTrue(result.isFavorite());
        assertEquals(favorite.getCreatedAt(), result.getFavoriteDate());
        assertEquals(article.getTitle(), result.getTitle());
        assertEquals(article.getSummary(), result.getSummary());
        assertEquals(article.getPublishedAt(), result.getPublishedAt());
    }

    @Test
    public void findByIdFavoriteDoesNotExist() {
        Long id = 1L;
        User user = new User();
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        when(favoriteRepository.findByUserAndArticle_ApiId(user, id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            articleService.findById(id);
        });

        assertEquals("The article is not found in your favorites", exception.getMessage());
    }

    @Test
    public void deleteByIdFavoriteExists() {
        Long id = 1L;
        User user = new User();

        Favorite favorite = new Favorite();
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        when(favoriteRepository.findByUserAndArticle_ApiId(user, id)).thenReturn(Optional.of(favorite));

        articleService.deleteById(id);

        assertTrue(favorite.getIsDeleted());
        verify(favoriteRepository).save(favorite);
    }

    @Test
    public void deleteByIdFavoriteDoesNotExist() {
        Long id = 1L;
        User user = new User();

        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        when(favoriteRepository.findByUserAndArticle_ApiId(user, id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            articleService.deleteById(id);
        });

        assertEquals("The article is not found in your favorites", exception.getMessage());
    }


}