export interface Article {
    id?: number;
    title: string;
    summary: string;
    publishedAt: Date;
    apiId:number;
    favorite: boolean;
    favoriteDate?: Date
}