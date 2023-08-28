import { Component, Input } from '@angular/core';
import { Article } from '../../models/article.models';
import { ArticleService } from '../../services/article.service';
import { AuthService } from '../../services/auth.service';
import { MatDialog } from '@angular/material/dialog';
import { AuthFormComponent } from '../auth-form/auth-form.component';

@Component({
  selector: 'app-article-card',
  templateUrl: './article-card.component.html',
  styleUrls: ['./article-card.component.scss']
})
export class ArticleCardComponent {

  @Input() article: Article | undefined;
  isFavorite: boolean = false;

  constructor(private articleService: ArticleService, private authService: AuthService, private matDialog: MatDialog) {
  }

  addFavorite() {
    if (this.authService.isAuthenticated()) {
      this.articleService.addFavoriteArticle(this.article!)
        .then((article) => {
          this.article = article
        });
    } else {
      this.matDialog.open(AuthFormComponent)
    }


  }

  deleteFavorite() {
    this.articleService.deleteFavorite(this.article!)
      .then((article) => {
        this.article = article
      });
  }





}
