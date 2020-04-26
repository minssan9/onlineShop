import Vue from 'vue'
import App from './App.vue'
import axios from 'axios'
import router from './router'
import store from './store/index.js'

new Vue({
  el: '#app',
  router,
  axios,
  store,
  render: h => h(App)
})
