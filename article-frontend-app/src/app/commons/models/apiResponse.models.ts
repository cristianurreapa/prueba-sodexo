import { ArticleApi } from "./articleApi.models";

export interface ApiResponse {
    count: number;
    next: string | null;
    previous: string | null;
    results: ArticleApi[];
  }