import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';


import {MatCardModule} from '@angular/material/card';
import {MatButtonModule} from '@angular/material/button';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatIconModule} from '@angular/material/icon';
import {ScrollingModule} from '@angular/cdk/scrolling';
import {MatInputModule} from '@angular/material/input';
import {MatDialogModule} from '@angular/material/dialog';
import {MatPaginatorModule} from '@angular/material/paginator';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthFormComponent } from './commons/components/auth-form/auth-form.component';
import { RegisterFormComponent } from './commons/components/register-form/register-form.component';
import { ArticleListComponent } from './commons/components/article-list/article-list.component';
import { ArticlesComponent } from './pages/public/articles/articles.component';
import { FavoritesComponent } from './pages/secure/favorites/favorites.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { AuthGuard } from './commons/guard/auth.guard';
import { ArticleCardComponent } from './commons/components/article-card/article-card.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { SideBarComponent } from './commons/components/side-bar/side-bar.component';
import { AuthInterceptor } from './commons/interceptors/auth.interceptor';
import { ErrorDialogComponent } from './commons/components/error-dialog/error-dialog.component';
import {MatSelectModule} from '@angular/material/select';
import { AuthService } from './commons/services/auth.service';


@NgModule({
  declarations: [
    AppComponent,
    AuthFormComponent,
    RegisterFormComponent,
    ArticleListComponent,
    ArticlesComponent,
    FavoritesComponent,
    ArticleCardComponent,
    SideBarComponent,
    ErrorDialogComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    BrowserAnimationsModule,

    MatCardModule,
    MatButtonModule,
    MatGridListModule,
    MatIconModule,
    ScrollingModule,
    MatInputModule,
    MatDialogModule,
    MatPaginatorModule,
    MatSelectModule
  ],
  providers: [  AuthService, AuthGuard, { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }],
  bootstrap: [AppComponent]
})
export class AppModule { }
