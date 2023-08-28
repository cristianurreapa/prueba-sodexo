import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ArticleFavorite } from '../../models/articleFavorite.models';
import { lastValueFrom } from 'rxjs';
import { Article } from '../../models/article.models';
import { GetArticleParams } from '../../models/getArticleParams.models';
import { ArticlePageable } from '../../models/articlesPageable.models';
import { Articles } from '../../models/articles.models';

@Injectable({
  providedIn: 'root'
})
export class FavoriteArticleApiService {

  private articleFavoriteApi: string = "http://localhost:8080/api/v1/article"

  constructor(private httpClient: HttpClient) { }

  async addFavoritePost(article: Article) {
    const response = this.httpClient.post<Article>(`${this.articleFavoriteApi}`, article);
    return await lastValueFrom(response);
  }

  async findFavorite(getArticleParams: GetArticleParams): Promise<ArticlePageable> {

    let url = `${this.articleFavoriteApi}/favorites?size=${getArticleParams.limit}&order=${getArticleParams.order}`;

    if (!(getArticleParams.offset === undefined)) {
      url = url + `&page=${getArticleParams.offset}`;
    }

    if (!(getArticleParams.filter === undefined)) {
      url = url + `&title=${getArticleParams.filter}&order=${getArticleParams.order}`;
    }

    const response = this.httpClient.get<ArticlePageable>(`${url}`);
    return await lastValueFrom(response);
  }

  async isFavorite(article: Articles): Promise<Articles> {
    const response = this.httpClient.post<Articles>(`${this.articleFavoriteApi}/isFavorite`, article);
    return await lastValueFrom(response);
  }

  async deleteFavorite(article: Article): Promise<void> {
    const response = this.httpClient.delete<void>(`${this.articleFavoriteApi}/${article.apiId}`);
    return await lastValueFrom(response);
  }
}
