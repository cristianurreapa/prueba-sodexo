import { NgModule, inject } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FavoritesComponent } from './pages/secure/favorites/favorites.component';
import { AuthGuard } from './commons/guard/auth.guard';
import { ArticlesComponent } from './pages/public/articles/articles.component';

const routes: Routes = [
  {
    path: '', component: ArticlesComponent
  },
{

  path: 'favorite', component: FavoritesComponent, canActivate: [AuthGuard],
},
{
  path: '**', component: ArticlesComponent
}];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
