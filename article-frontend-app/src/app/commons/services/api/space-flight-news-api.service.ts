import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { GetArticleParams } from '../../models/getArticleParams.models';
import { ApiResponse } from '../../models/apiResponse.models';
import { lastValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SpaceFlightNewsApiService {

  private baseUrl: string = "http://api.spaceflightnewsapi.net/v4/articles?apiKey=d4105aec730746b294adbe31e7c3ddb3"

  constructor(private httpClient: HttpClient) {
  }

  async getArticleFilter(getArticleParams: GetArticleParams): Promise<ApiResponse> {

    let url = `${this.baseUrl}&limit=${getArticleParams.limit}`;

    if (!(getArticleParams.offset === undefined)) {
      url = url + `&offset=${getArticleParams.offset}`;
    }

    if (!(getArticleParams.filter === undefined)) {
      url = url + `&title_contains_all=${getArticleParams.filter}&ordering=${getArticleParams.order}`
      console.log(url)
    }

    const response = this.httpClient.get<ApiResponse>(url);

    return await lastValueFrom(response);
  }



}
