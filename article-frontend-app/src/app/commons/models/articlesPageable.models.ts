import { Article } from "./article.models";

export interface ArticlePageable{
    sizeContent: number,
    index: number,
    data: Article[]
}