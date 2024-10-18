import Vue from 'vue'
import VueRouter from 'vue-router'
import SignInView from "../views/SignInView";
import AddArticleView from "../views/AddArticleView";
import AddUserView from "@/views/AddUserView";
import EditUserView from "@/views/EditUserView";
import CategoryView from "@/views/CategoryView";
import EditCategoryView from "@/views/EditCategoryView";
import NewCategoryView from "@/views/NewCategoryView";
import ArticlesView from "@/views/ArticlesView";
import ArticlesByCategory from "@/views/ArticlesByCategory";
import UsersView from "@/views/UsersView";
import EditArticleView from "@/views/EditArticleView";
import HomeView from "@/views/HomeView";
import PublicArtByCatView from "@/views/PublicArtByCatView";
import MostReadView from "@/views/MostReadView";
import ArticlesByTagView from "@/views/ArticlesByTagView";
import SingleArticleVIew from "@/views/SingleArticleVIew";
import ArticlesSearchedView from "@/views/ArticlesSearchedView";
import HomeSearchedView from "@/views/HomeSearchedView";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'HomeView',
    component: HomeView
  },
  {
    path: '/signIn',
    name: 'SignInView',
    component: SignInView
  },
  {
    path: '/addArticle',
    name: 'AddArticleView',
    component: AddArticleView
  },
  {
    path: '/cms_addUser',
    name: 'AddUserView',
    component: AddUserView
  },
  {
    path: '/cms_editUser/:id',
    name: 'EditUserView',
    component: EditUserView
  },
  {
    path: '/categories',
    name: 'CategoryView',
    component: CategoryView
  },

  {
    path: '/edit-category/:id',
    name: 'EditCategoryView',
    props: true,
    component: EditCategoryView
  },
  {
    path: '/new-category',
    name: 'NewCategoryView',
    component: NewCategoryView
  },
  {
    path: '/articles',
    name: 'ArticlesView',
    component: ArticlesView
  },
  {
    path: '/editArticle/:id',
    name: 'EditArticleView',
    component: EditArticleView
  },
  {
    path: '/articlesByCategory/:id',
    name: 'ArticlesByCategory',
    component: ArticlesByCategory
  },
  {
    path: '/users',
    name: 'UsersView',
    component: UsersView
  },
  {
    path: '/publicArtByCategory/:id',
    name: 'PublicArtByCategory',
    component: PublicArtByCatView
  },
  {
    path: '/mostRead',
    name: 'MostRead',
    component: MostReadView
  },
  {
    path: '/tag/:id',
    name: 'ByTag',
    component: ArticlesByTagView
  },
  {
    path: '/articles/:id',
    name: 'SingleArticleVIew',
    component: SingleArticleVIew
  },
  {
    path: '/articles/search/:search',
    name: 'ArticlesSearchedView',
    component: ArticlesSearchedView
  },
  {
    path: '/search/:search',
    name: 'HomeSearchedView',
    component: HomeSearchedView
  },
]

const router = new VueRouter({
  routes
})

export default router
