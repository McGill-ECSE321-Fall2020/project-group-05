import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/search',
    name: 'Search',
    component: () => import(/* webpackChunkName: "about" */ '../views/Search.vue')
  },
  {
    path: '/artlisting/create',
    name: 'CreateListing',
    component: () => import(/* webpackChunkName: "about" */ '../views/CreateListing.vue')
  },
  {
    path: '/about',
    name: 'About',
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  },
  {
    path: '/userpage/:id',
    name: 'UserPage',
    component: () => import(/* webpackChunkName: "about" */ '../views/UserPage.vue')
  },
  {
    path: '/artistpage/:id',
    name: 'ArtistPage',
    component: () => import(/* webpackChunkName: "about" */ '../views/ArtistPage.vue')
  },
  {
    path: '/purchasepage/:id',
    name: 'PurchasePage',
    component: () => import(/* webpackChunkName: "about" */ '../views/PurchasePage.vue')
  },
  {
    path: '/basiclogin',
    name: 'BasicLogin',
    component: () => import('../views/BasicLogin.vue')
  },
  {
    path: '/examplepage',
    name: 'ExamplePage',
    component: () => import('../views/ExamplePage.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
