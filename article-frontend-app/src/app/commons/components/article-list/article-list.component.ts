import { Component, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { ArticleService } from '../../services/article.service';
import { debounceTime } from 'rxjs';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { GetArticleParams } from '../../models/getArticleParams.models';
import { Articles } from '../../models/articles.models';
import { Router } from '@angular/router';

@Component({
  selector: 'app-article-list',
  templateUrl: './article-list.component.html',
  styleUrls: ['./article-list.component.scss']
})
export class ArticleListComponent {

  offset: number = 0;
  search: FormGroup;
  articles: Articles | undefined;
  favorite: boolean = false;
  @Output() find = new EventEmitter<GetArticleParams>();
  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator | undefined;

  constructor(private apiService: ArticleService, private fb: FormBuilder, private router: Router) {

    let orderValue = "-published_at";

    if (this.router.url.includes("favorite")) {
      this.favorite = true;
      orderValue = "asc";
    }

    this.search = this.fb.group(
      {
        filter: ['', Validators.required],
        order: [orderValue]
      }
    )

    this.search.get("filter")?.valueChanges.pipe(
      debounceTime(500)
    ).subscribe((val: string) => {
      if (this.paginator) {
        this.paginator.pageIndex = 0;
      }

      let params: GetArticleParams = {
        limit: 10,
        filter: val,
        order: this.search.get("order")?.value
      }
      this.find.emit(params);
    })



    this.apiService.article$.subscribe((data) => {
      this.articles = data;
    })
  }

  paginater() {
    this.offset = this.paginator?.pageIndex! * this.paginator?.pageSize!;
    if (this.favorite) {
      this.offset = this.paginator?.pageIndex!;
    }
    let params: GetArticleParams = {
      limit: 10,
      offset: this.offset,
      filter: this.search.get("filter")?.value,
      order: this.search.get("order")?.value
    }
    this.find.emit(params);
  }

  order() {
    let params: GetArticleParams = {
      limit: 10,
      offset: this.offset,
      filter: this.search.get("filter")?.value,
      order: this.search.get("order")?.value
    }
    if (this.paginator) {
      this.paginator.pageIndex = 0;
    }
    this.find.emit(params);
  }

}
