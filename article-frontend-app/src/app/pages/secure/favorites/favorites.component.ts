import { Component } from '@angular/core';
import { Articles } from 'src/app/commons/models/articles.models';
import { GetArticleParams } from 'src/app/commons/models/getArticleParams.models';
import { ArticleService } from 'src/app/commons/services/article.service';

@Component({
  selector: 'app-favorites',
  templateUrl: './favorites.component.html',
  styleUrls: ['./favorites.component.scss']
})
export class FavoritesComponent {
  
  articles!: Articles;

  constructor(private articleService: ArticleService
  ){

  let getArticleParams: GetArticleParams = {
    limit: 10,
    offset: 0,
    order: "asc"
  }

  this.findArticle(getArticleParams);
  }

  findArticle(getArticleParams: GetArticleParams) {
    this.articleService.getFavoriteArticle(getArticleParams);
  }
}
