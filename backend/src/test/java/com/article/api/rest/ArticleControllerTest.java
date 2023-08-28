package com.article.api.rest;


import com.article.api.domain.ArticleDto;
import com.article.api.domain.ArticlesPageable;
import com.article.api.entities.Article;
import com.article.api.entities.Articles;
import com.article.api.service.ArticleService;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@AllArgsConstructor
class ArticleControllerTest {

    @Mock
    private ArticleService articleService;

    @InjectMocks
    private ArticleController articleController;

    @Test
    public void testFindById() {
        Long id = 1L;
        ArticleDto article = new ArticleDto();
        article.setId(id);

        when(articleService.findById(id)).thenReturn(article);

        ArticleDto found = articleController.findById(id);

        assertNotNull(found);
        assertEquals(found.getId(), id);
    }

    @Test
    public void testFindAll() {
        Pageable pageable = PageRequest.of(0, 10);
        String title = "test title";
        String order = "desc";
        ArticlesPageable articlesPageable = new ArticlesPageable();

        when(articleService.findAll(pageable, title, order)).thenReturn(articlesPageable);

        ArticlesPageable found = articleController.findAll(pageable, title, order);

        assertNotNull(found);
    }

    @Test
    public void testCreate() {
        ArticleDto articleDto = new ArticleDto();
        articleDto.setTitle("Test title");

        when(articleService.save(articleDto)).thenReturn(articleDto);

        ArticleDto created = articleController.create(articleDto);

        assertNotNull(created);
        assertEquals(created.getTitle(), articleDto.getTitle());
    }

    @Test
    public void testIsFavorite() {
        Articles articles = new Articles();
        articles.setData(List.of(new ArticleDto()));

        when(articleService.isFavorite(articles)).thenReturn(articles);

        Articles result = articleController.isFavorite(articles);

        assertNotNull(result.getData());
    }

    @Test
    public void testUpdate() {
        Article article = new Article();
        article.setId(1L);
        article.setTitle("Updated Title");

        when(articleService.update(article)).thenReturn(article);

        Article updated = articleController.update(article);

        assertNotNull(updated);
        assertEquals(updated.getId(), article.getId());
        assertEquals(updated.getTitle(), article.getTitle());
    }

    @Test
    public void testDelete() {
        Long id = 1L;

        doNothing().when(articleService).deleteById(id);
        articleController.delete(id);
        verify(articleService, times(1)).deleteById(id);
    }

}