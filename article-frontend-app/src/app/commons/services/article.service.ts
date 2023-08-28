
import { Injectable } from '@angular/core';
import { Article } from '../models/article.models';
import { GetArticleParams } from '../models/getArticleParams.models';
import { SpaceFlightNewsApiService } from './api/space-flight-news-api.service';
import { ArticleApi } from '../models/articleApi.models';
import { FavoriteArticleApiService } from './api/favorite-article-api.service';
import { Articles } from '../models/articles.models';
import { BehaviorSubject } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  private articleSubject = new BehaviorSubject<Articles>({});
  public article$ = this.articleSubject.asObservable();

  constructor(private spaceFlightNewsApi: SpaceFlightNewsApiService, private favoriteArticleApi: FavoriteArticleApiService, private authService: AuthService) { }

  async getArticle(getArticleParams: GetArticleParams) {

    let articlesList: Article[] = [];
    let articles: Articles = {};

    await this.spaceFlightNewsApi.getArticleFilter(getArticleParams).then(async (data) => {
      articles.count = data.count;
      data.results.forEach((article) => {
        articlesList.push(this.parseArticle(article));
      })

      articles.data = articlesList;
      if (this.authService.isAuthenticated()) {
        await this.favoriteArticleApi.isFavorite(articles).then((data) => {
          console.log(data)
          articles.data = data.data
        });
      }

      this.articleSubject.next(articles);
    });
  }

  async addFavoriteArticle(article: Article): Promise<Article> {

    await this.favoriteArticleApi.addFavoritePost(article)
      .then((data) => article = data)
      .catch((error) => console.log(error))

    console.log(article)
    return article;
  }

  async deleteFavorite(article: Article) {
    await this.favoriteArticleApi.deleteFavorite(article).then((data) => {
      article.favorite = false;
    })

    return article;
  }

  async getFavoriteArticle(getArticleParams: GetArticleParams) {

    let articles: Articles = {};

    await this.favoriteArticleApi.findFavorite(getArticleParams).then((data) => {
      articles.count = data.sizeContent;
      articles.data = data.data
      this.articleSubject.next(articles);
    });
  }

  private parseArticle(data: ArticleApi): Article {
    let article: Article = {
      title: data.title,
      summary: data.summary,
      publishedAt: new Date(data.published_at),
      favorite: false,
      apiId: data.id
    };

    return article;
  }

}
