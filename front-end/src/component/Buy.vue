<template>
  <div class="mt-3 mb-5">
    <BuyList/>
    <Placer :account="account"/>

    <div class="container text-right">
      <button :disabled="buyList.length<=0" @click="showModal=true" class="btn btn-secondary"> 주문하기</button>
      <router-link to="/">
        <button @click="CLEAR_BUY_LIST" class="btn btn-light btn-outline-dark text-right">취소하기</button>
      </router-link>
    </div>

    <Modal v-if="isOrder">
      <h3 slot="header">구매</h3>
      <div slot="body">
        정말 구매하시겠습니까?
      </div>
      <span slot="footer" @click="showModal=false">
      <button @click.prevent="onBuy" class="btn btn-sm btn-warning">구매하기</button>
      <button class="btn btn-sm btn-warning">취소하기</button>
    </span>
    </Modal>
  </div>

</template>

<script>

  import BuyList from './buy/BuyList'
  import Placer from './buy/Placer'
  import Modal from './Modal'
  import {mapState, mapMutations, mapActions} from 'vuex'

  export default {
    data () {
      return {
        showModal: false
      }
    },
    components: {
      BuyList,
      Placer,
      Modal
    },
    computed: {
      ...mapState([
        'account',
        'buyList'
      ]),
      isOrder () {
        return this.showModal
      }
    },
    methods: {
      ...mapActions([
        'FETCH_ACCOUNT',
        'ADD_ORDER'
      ]),
      ...mapMutations([
        'CLEAR_BUY_LIST'
      ]),
      onBuy () {
        this.ADD_ORDER(this.buyList)
        this.$router.push('/')
      }
    },
    created () {
      this.FETCH_ACCOUNT()
    }
  }
</script>

<style scoped>

</style>
