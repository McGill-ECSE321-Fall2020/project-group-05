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
    path: '/login',
    name: 'LoginPage',
    component: () => import(/* webpackChunkName: "about" */ '../views/LoginPage.vue')
  },
  {
    path: '/signup',
    name: 'SignupPage',
    component: () => import(/* webpackChunkName: "about" */ '../views/SignupPage.vue')
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
    path: '/managerpage/:id',
    name: 'ManagerPage',
    component: () => import(/* webpackChunkName: "about" */ '../views/ManagerPage.vue')
  },
  {
    path: '/purchasepage/:id',
    name: 'PurchasePage',
    component: () => import(/* webpackChunkName: "about" */ '../views/PurchasePage.vue')
  },
  {
    path: '/checkout/:id',
    name: 'checkout',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/checkout.vue')
  },
  {
    path: '/ordersuccess',
    name: 'OrderSuccessPage',
    component: () => import('../views/orderSuccess.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
