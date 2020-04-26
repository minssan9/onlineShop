import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../component/Home.vue'
import List from '../component/item/list.vue'
import Item from '../component/Item.vue'
import Mypage from '../component/Mypage'
import Setting from '../component/myPage/Setting.vue'
import Buy from '../component/Buy'
import Login from '../component/Login'
import Join from '../component/Join'
import QnAManagement from '../component/manager/qna/QnAManagement'
import Cart from '../component/cart/Cart'
import Chat from '../component/serviceCenter/Chat'
import Order from '../component/manager/order/OrderManagement'
import AccountManagement from '../component/manager/account/AccountManagement'
import ProductPick from '../component/manager/product/ProductManagementPick'
import ProductRegister from '../component/manager/product/ProductRegisterManagement'
import ProductUpdate from '../component/manager/product/ProductUpdateManagement'
import ProductList from '../component/manager/product/ProductListManagement'
import Review from '../component/manager/review/ReviewManagement'
import StatisticsManagement from '../component/manager/statistics/StatisticsManagement'
import ChatManagement from '../component/manager/chat/ChatManagement'
import MailManagement from '../component/manager/mail/MailManagement'
import HomeManagement from '../component/manager/home/HomeManagement'
import store from '../store'


Vue.use(VueRouter)

const requireAuth = (to, from, next) => {
  !!store.state.access_token ? next() : next(`/login?returnPath=${encodeURIComponent(from.path)}`)
}

const requireManager = (to, from, next) => {

  // 액세스토큰이 있으면
  if (!!store.state.access_token) {
    // 권한이 ADMIN 이면
    if (!!store.state.account.roles && !!(store.state.account.roles.includes('ADMIN') ))
      return next()
    else {
      alert('권한이 없습니다.')
      return next('/')
    }
  } else
    return next(`/login?returnPath=${encodeURIComponent(from.path)}`)


}

const router = new VueRouter({
  mode: 'history',

  // 스크롤 클릭시 맨 위로
  scrollBehavior(to, from, savedPosition) {
    return {x: 0, y: 0}
  },

  routes: [
    {
      path: '/',
      component: Home
    },
    {
      path: '/join',
      component: Join
    },
    {
      path: '/login',
      component: Login
    },
    {
      path: '/item/list',
      component: List,
    },
    {
      path: '/item/:id',
      component: Item,
      beforeEnter: requireAuth
    },
    {
      path: '/myPage',
      component: Mypage,
      beforeEnter: requireAuth
    },
    {
      path: '/myPage/setting',
      component: Setting,
      beforeEnter: requireAuth
    },
    {
      path: '/buy',
      component: Buy,
      beforeEnter: requireAuth
    },
    {
      path:'/cart',
      component:Cart,
      beforeEnter: requireAuth
    },
    {
      path:'/serviceCenter/:id',
      component:Chat,
      beforeEnter: requireAuth
    },
    {
      path: '/manager/Home',
      component: HomeManagement,
      beforeEnter: requireManager
    },
    {
      path: '/manager/product/register',
      component: ProductRegister,
      beforeEnter: requireManager
    },
    {
      path: '/manager/product/update/:id',
      component: ProductUpdate,
      beforeEnter: requireManager
    },
    {
      path: '/manager/product/list',
      component: ProductList,
      beforeEnter: requireManager
    },
    {
      path: '/manager/product/pick',
      component: ProductPick,
      beforeEnter: requireManager
    },
    {
      path: '/manager/qna',
      component: QnAManagement,
      beforeEnter: requireManager
    },
    {
      path: '/manager/order',
      component: Order,
      beforeEnter: requireManager
    },
    {
      path: '/manager/account',
      component: AccountManagement,
      beforeEnter: requireManager
    },
    {
      path: '/manager/review',
      component: Review,
      beforeEnter: requireManager
    },
    {
      path: '/manager/chat',
      component: ChatManagement,
      beforeEnter: requireManager
    },
    {
      path: '/manager/chat/:id',
      component: ChatManagement,
      beforeEnter: requireManager
    },
    {
      path: '/manager/mail',
      component:MailManagement
    },
    {
      path: '/manager/statistics',
      component: StatisticsManagement,
      beforeEnter: requireManager
    }
  ]
})

export default router
