package com.article.api.entities;

import com.article.api.domain.ArticleDto;
import lombok.Data;

import java.util.List;

@Data
public class Articles {

    private List<ArticleDto> data;
}
