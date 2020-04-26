<template>

  <div class="row m-3">
    <Sidebar/>

    <div class="col-10">

      <div class="container">
        <div class="list-group-item mt-3 ">
          <p><strong>상품 기본정보</strong></p>
          <hr class="border-secondary">
          <div class="row">
            <strong>상품번호</strong>
            <input :disabled=true class="form-control" :value="`${item.id}`">

            <strong>상품명</strong>
            <input v-model="updateItem.title" type="text" class="form-control" placeholder="상품명">
            <strong>설명</strong>
            <textarea v-model="updateItem.content" class="form-control" rows="5"></textarea>

          </div>
        </div>

        <div class="list-group-item mt-3">
          <div class="row">
            <div class="col-sm-4">
              <strong>정가</strong>
              <input v-model="updateItem.price" type="text" class="form-control" placeholder="정가">
            </div>
            <div class="col-sm-4">
              <strong>할인가</strong>
              <input v-model="updateItem.discount" type="text" class="form-control" placeholder="할인가">
            </div>
            <div class="col-sm-4">
              <strong>배송예정일</strong>
              <input v-model="updateItem.deliveryDate" type="text" class="form-control" placeholder="배송예정일">
            </div>
          </div>
        </div>

        <div class="list-group-item mt-3">

          <strong>파일업로드</strong>
          <input id="file" name="file" type="file" class="form-control-file" multiple>
        </div>

        <div class="text-right mt-3 mb-3">
          <button @click="showModal=true" class="btn btn-warning">상품수정</button>
          <router-link class="btn btn-warning" to="/manager/product/list">취소</router-link>
        </div>

        <Modal v-if="isRegister">
          <h3 slot="header">수정</h3>
          <div slot="body">
            정말 수정하시겠습니까?
          </div>
          <span slot="footer" @click="showModal=false">
      <button @click="onUpdateItem" class="btn btn-sm btn-warning">수정하기</button>
      <button class="btn btn-sm btn-warning">취소하기</button>
    </span>
        </Modal>

      </div>
    </div>
  </div>
</template>

<script>

  import {mapState, mapActions} from 'vuex'
  import Modal from '../../Modal'
  import Sidebar from '../Sidebar'

  export default {
    data () {
      return {
        updateItem: {
          title: '',
          content: '',
          price: '',
          discount: '',
          deliveryDate: '',
          itemFileList: []
        },
        showModal: false
      }
    },
    computed: {
      ...mapState([
        'item'
      ]),
      isRegister () {
        return this.showModal
      }
    },
    components: {
      Modal,
      Sidebar
    },
    methods: {
      ...mapActions([
        'UPDATE_ITEM',
        'FETCH_ITEM'
      ]),
      onUpdateItem () {
        this.UPDATE_ITEM({id: this.$route.params.id, item: this.updateItem})
        alert('수정이 완료되었습니다.')
        this.$router.push('/')
      }
    },
    created () {
      this.FETCH_ITEM(this.$route.params.id)
      this.updateItem.title = this.item.title
      this.updateItem.content = this.item.content
      this.updateItem.price = this.item.price
      this.updateItem.discount = this.item.discount
      this.updateItem.deliveryDate = this.item.deliveryDate
    }

  }
</script>

<style scoped>

</style>
