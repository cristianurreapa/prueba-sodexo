import { Article } from "./article.models";

export interface ArticleFavorite {
    id?: number;
    article: Article;
    createdAt?: Date;
  }