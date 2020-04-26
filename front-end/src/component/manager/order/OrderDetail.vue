<template>
  <div>
    <div v-for="order in orderList.content" :key="order.id">
      <div class="row mt-2">
        <div class="col-2">
          <router-link :to="`/item/${order.item.id}`">{{order.item.title}}</router-link>
        </div>
        <div class="col-2">
          <router-link to="/manager/account">{{order.account.accountId}}</router-link>
        </div>
        <div class="col-1">
          <p> {{order.option}} </p>
        </div>
        <div class="col-1">
          <p> {{order.payment}} </p>
        </div>
        <div class="col-2">
          <p> {{order.created}} </p>
        </div>
        <div class="col-2">
          <span v-if="order.status===0" class="badge badge-warning">결제완료</span>
          <span v-else-if="order.status===1" class="badge badge-success">배송준비</span>
          <span v-else-if="order.status===2" class="badge badge-danger">배송중</span>
          <span v-else-if="order.status===3" class="badge badge-primary">배송완료</span>
          <span v-else-if="order.status===4" class="badge badge-dark">결제확정</span>
          <span v-else-if="order.status===5" class="badge badge-info">거래취소</span>
        </div>
        <div class="col-2">
          <button class="btn btn-sm btn-outline-primary" @click="onModal(order.id)"> 관리</button>
        </div>
      </div>
      <hr/>
    </div>
    <div class="m-5 text-center" v-if="!orderList || orderList.totalPages===0">
      검색 결과가 없습니다.
    </div>

  </div>
</template>

<script>
  import {mapState} from 'vuex'

  export default {

    computed: {
      ...mapState([
        'orderList',
      ])
    },
    methods: {
      onModal(id) {
        this.$emit('@Modal', id)
      }
    }
  }

</script>

<style scoped>

</style>
