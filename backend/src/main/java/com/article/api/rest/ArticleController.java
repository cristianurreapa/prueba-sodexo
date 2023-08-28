package com.article.api.rest;

import com.article.api.entities.Article;
import com.article.api.domain.ArticleDto;
import com.article.api.entities.Articles;
import com.article.api.domain.ArticlesPageable;
import com.article.api.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/article")
@CrossOrigin(originPatterns = {"http://localhost:4200"})
public class ArticleController {

    private final ArticleService articleService;

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ArticleDto findById(@PathVariable Long id) {
        return articleService.findById(id);
    }

    @RequestMapping(value = "/favorites",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ArticlesPageable findAll(Pageable pageable, @RequestParam(required = false) String title, @RequestParam String order) {
        return articleService.findAll(pageable, title, order);
    }

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ArticleDto create(@RequestBody ArticleDto articleDto) {
        return articleService.save(articleDto);
    }

    @RequestMapping(
            value = "/isFavorite",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Articles isFavorite(@RequestBody Articles articles) {
        return articleService.isFavorite(articles);
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Article update( @RequestBody Article article) {
        return articleService.update(article);
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void delete(@PathVariable Long id) {
        articleService.deleteById(id);
    }
}
