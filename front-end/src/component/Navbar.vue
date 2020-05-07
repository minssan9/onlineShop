<template>

  <nav class="navbar navbar-expand-sm navbar-light bg-white">
    <a class="navbar-brand font-weight-bold" href="/"> Minssan9 </a>

    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#alt" aria-controls="alt"
            aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="alt">
      <div class="navbar-nav ml-auto">
        <router-link to="/item/list" class="nav-item nav-link font-weight-bold">Shop</router-link>
        <router-link v-if="!isAuthenicated" to="/join" class="nav-item nav-link  font-weight-bold">Join</router-link>
        <router-link v-if="isAuthenicated" to="/myPage" class="nav-item nav-link  font-weight-bold">MyPage</router-link>
        <router-link v-if="isAuthenicated" to="/cart" class="nav-item nav-link  font-weight-bold">Cart</router-link>
        <router-link v-if="isAuthenicated" :to="`/serviceCenter/${account.chat}`" class="nav-item nav-link  font-weight-bold">ServiceCenter</router-link>
        <router-link v-if="isAuthenicated && isManager" to="/manager/home" class="nav-item nav-link  font-weight-bold">Manager</router-link>    <!-- 관리자일경우-->
        <div>
          <a v-if="isAuthenicated" class="nav-item nav-link font-weight-bold" href="" @click.prevent="logout">LOGOUT</a>
          <router-link v-else to="/login" class="nav-item nav-link  font-weight-bold">LOGIN</router-link>
        </div>
      </div>
    </div>

  </nav>
</template>

<script>

  import {mapState} from 'vuex'

  export default {

    computed: {
      ...mapState([
        'access_token',
        'account'
      ]),
      isAuthenicated() {
        return !!this.access_token
      },
      isManager(){
        if(!this.account.roles)
          return false

        if(!this.account.roles.includes('ADMIN'))
          return false

        return true
      }
    },
    methods: {
      logout() {
        this.$store.commit('LOGOUT')
        location.href = '/login'
      }
    }
  }
</script>

<style scoped>

</style>
