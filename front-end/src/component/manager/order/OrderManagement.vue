<template>
  <div>
    <div class="row mb-5">
      <Sidebar/>
      <div class="col-10 mt-5 mx-auto">
        <div class="card">
          <div class="card-header">
            <h6 class="text-primary font-weight-bold">주문현황</h6>
          </div>
          <div class="card-body pt-0">
            <div class="table">
              <div class="row">
                <th class="col-2">상품명</th>
                <th class="col-2">주문자</th>
                <th class="col-1">옵션</th>
                <th class="col-1">결제방법</th>
                <th class="col-2">주문날짜</th>
                <th class="col-2">주문상태</th>
                <th class="col-2">상태관리</th>
              </div>
              <hr/>
              <OrderDetail v-on:@Modal="onModal"/>
              <OrderPagination/>
              <OrderSearchForm/>
            </div>
          </div>
        </div>
      </div>
    </div>

    <Modal v-if="isModal">
      <h3 slot="header">상태관리</h3>
      <div class="row" slot="body">
        <button @click="onChange(1)" class="btn btn-sm btn-success">배송준비</button>
        <button @click="onChange(2)" class="btn btn-sm btn-danger">배송중</button>
        <button @click="onChange(5)" class="btn btn-sm btn-info">거래취소</button>
      </div>
      <span slot="footer" @click="showModal=false">
             <p v-if="playload.status===1">선택:배송준비</p>
             <p v-else-if="playload.status===2">선택:배송중</p>
             <p v-else-if="playload.status===5">선택:거래취소</p>
        <button @click="onSubmit" class="btn btn-sm btn-warning">수정</button>
        <button class="btn btn-sm btn-warning">취소</button>
      </span>
    </Modal>

  </div>
</template>

<script>
  import {mapMutations, mapActions} from 'vuex'
  import Sidebar from '../Sidebar'
  import OrderDetail from './OrderDetail'
  import Modal from '../../Modal'
  import OrderSearchForm from "../order/OrderSearchForm"
  import OrderPagination from '../order/OrderPagination'

  export default {
    data() {
      return {
        showModal: false,
        playload: {
          status: '',
          id: '',
        }
      }
    },
    components: {
      OrderSearchForm,
      OrderDetail,
      OrderPagination,
      Sidebar,
      Modal
    },
    computed: {
      isModal() {
        return this.showModal
      }
    },
    methods: {
      onModal(id) {
        this.showModal = true
        this.playload.id = id
      },
      ...mapMutations([
        'CLEAR_SEARCH_MODE'
      ]),
      ...mapActions([
        'UPDATE_ORDER'
      ]),
      onChange(status) {
        if (status === 1) {
          this.playload.status = status
        } else if (status === 2) {
          this.playload.status = status
        } else if (status == 5) {
          this.playload.status = status
        }
      },
      onSubmit() {
        this.UPDATE_ORDER(this.playload)
      },
      created() {
        this.CLEAR_SEARCH_MODE()
      }
    }
  }
</script>

<style scoped>

</style>
