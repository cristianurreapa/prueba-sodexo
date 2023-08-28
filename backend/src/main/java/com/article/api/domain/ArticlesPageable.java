package com.article.api.domain;

import lombok.Data;

import java.util.List;

@Data
public class ArticlesPageable {

    private Long sizeContent;

    private int index;

    private List<ArticleDto> data;


}
