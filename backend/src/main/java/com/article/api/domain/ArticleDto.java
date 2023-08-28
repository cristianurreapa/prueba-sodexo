package com.article.api.domain;

import lombok.Data;

import java.util.Date;


@Data
public class ArticleDto {

    private Long id;

    private String title;

    private String summary;

    private Date publishedAt;

    private Long apiId;

    private boolean favorite;

    private Date favoriteDate;
}
