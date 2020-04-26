<template>

  <div class="container mt-5 mb-5">
    <h3 class="text-center mb-3"> 장바구니 </h3>
    <table class="table">
      <thead>
      <tr>
        <th scope="col">#</th>
        <th scope="col">상품명</th>
        <th scope="col">판매가</th>
        <th scope="col">수량</th>
        <th scope="col">관리</th>
      </tr>
      </thead>
      <tbody>
      <CartItem v-for="(cart,index) in cartList" :key="index" :index="index" :cart="cart"/>
      </tbody>
    </table>

    <div v-if="cartList.length===0" class="text-center mt-5 mb-5">
      장바구니가 비어있습니다.
    </div>

    <div class="text-center mt-5">
      <button @click="onAllCartDelete" class="btn btn-lg btn-secondary">전 체 삭 제 하 기</button>
      <button @click="onBuy" class="btn btn-lg btn-dark">구 매 하 기</button>
    </div>
  </div>
</template>

<script>
  import CartItem from '../cart/CartItem'
  import {mapState,mapMutations, mapActions} from 'vuex'

  export default {

    components: {
      CartItem
    },
    computed: {
      ...mapState([
        'cartList'
      ])
    },
    methods: {
      ...mapMutations([
        'CART_TO_BUYLIST'
      ]),
      ...mapActions([
        'FETCH_ACCOUNT_CART',
        'DELETE_ACCOUNT_CART_ALL'
      ]),
      onAllCartDelete(){
        this.DELETE_ACCOUNT_CART_ALL()
      },
      onBuy(){
        this.FETCH_ACCOUNT_CART()
        this.CART_TO_BUYLIST()
        this.$router.push("/buy")
      }
    },
    created() {
      this.FETCH_ACCOUNT_CART()
    }
  }
</script>

<style scoped>

</style>
