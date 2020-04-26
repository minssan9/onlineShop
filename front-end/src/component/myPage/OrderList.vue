<template>
  <div class="row justify-content-center mt-3">
    <table class="table"
           style="text-align: center; border: 1px solid #dddddd">
      <thead>
      <tr>
        <th style="background-color: #eeeeee; text-align: center;">주문번호</th>
        <th style="background-color: #eeeeee; text-align: center;">상품정보</th>
        <th style="background-color: #eeeeee; text-align: center;">옵션</th>
        <th style="background-color: #eeeeee; text-align: center;">주문일자</th>
        <th style="background-color: #eeeeee; text-align: center;">결제방법</th>
        <th style="background-color: #eeeeee; text-align: center;">현황</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="order in orderList.content" :key="order.id">
        <td> {{order.id}}</td>
        <td>
          <div>
            <img class="mr-2" style="max-width: 50px"
                 :src="`http://localhost:8090/api/items/download?fileName=${order.item.itemFileList[0].uuid}_${order.item.itemFileList[0].fileName}`"
                 alt="img">
            <router-link :to="`/item/${order.item.id}`">{{order.item.title}}</router-link>
          </div>
        </td>
        <td> {{order.option}}</td>
        <td> {{order.created}}</td>
        <td> {{order.payment}}</td>

        <td>
          <span v-if="order.status===0" class="badge badge-warning">결제완료</span>
          <span v-else-if="order.status===1" class="badge badge-success">배송준비</span>
          <span v-else-if="order.status===2" class="badge badge-danger">배송중</span>
          <span v-else-if="order.status===3" class="badge badge-primary">배송완료</span>
          <span v-else-if="order.status===4" class="badge badge-dark">결제확정</span>
          <span v-else-if="order.status===5" class="badge badge-info">거래취소</span>
        </td>
      </tr>

      </tbody>
    </table>

    <div class="row">
      <div class="col-6">
        <input v-model="page" style="max-width: 100px" class="form-control" placeholder="ex)1,2..">
      </div>
      <div class="col-6">
        <span class="mr-2">[1~{{orderList.totalPages}}]</span>
        <button @click="onPage" class="btn btn-sm btn-outline-success">이동</button>
      </div>
    </div>

  </div>
</template>

<script>
  import {mapState} from 'vuex'
  import {mapActions} from 'vuex'

  export default {
    props: ['account'],
    data() {
      return {
        page: ''
      }
    },
    methods: {
      ...mapActions([
        'FETCH_ORDER_ACCOUNT_PAGE'
      ]),
      onPage() {
        this.FETCH_ORDER_ACCOUNT_PAGE({id: this.page - 1})
      }
    },
    computed: {
      ...mapState([
        'orderList'
      ])
    },
    created() {
      this.FETCH_ORDER_ACCOUNT_PAGE({id: 0})
    },

  }
</script>

<style scoped>

</style>
