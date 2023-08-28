import { Component } from '@angular/core';
import { Articles } from 'src/app/commons/models/articles.models';
import { GetArticleParams } from 'src/app/commons/models/getArticleParams.models';
import { ArticleService } from 'src/app/commons/services/article.service';

@Component({
  selector: 'app-articles',
  templateUrl: './articles.component.html',
  styleUrls: ['./articles.component.scss']
})
export class ArticlesComponent {

  articles!: Articles;

  constructor(private articleService: ArticleService
  ) {

    let getArticleParams: GetArticleParams = {
      limit: 10,
      offset: 0,
      order: 'published_at'
    }

    this.findArticle(getArticleParams);
  }


  findArticle(getArticleParams: GetArticleParams) {
    this.articleService.getArticle(getArticleParams);
  }

}
