<template>
  <div class="container mt-3 mb-3">
    <Profile :account="account"/>
    <OrderList/>
  </div>
</template>

<script>
  import Profile from './myPage/Profile'
  import OrderList from './myPage/OrderList'
  import {mapState, mapActions} from 'vuex'

  export default {
    components: {
      Profile,
      OrderList
    },
    methods: {
      ...mapActions([
        'FETCH_ACCOUNT'
      ])
    },
    computed: {
      ...mapState([
        'account'
      ])
    },
    created () {
      this.FETCH_ACCOUNT()
    }
  }
</script>

<style scoped>

</style>
