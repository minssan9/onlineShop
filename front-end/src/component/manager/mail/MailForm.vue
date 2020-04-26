<template>


  <div class="container">

    <div class="list-group-item mt-3 ">
      <p><strong>전체 회원에게 메일보내기</strong></p>
      <hr class="border-secondary">
      <div class="row ml-1 mr-1">
        <strong>제목</strong>
        <input v-model="title" type="text" class="form-control" placeholder="제목">
        <strong>내용</strong>
        <textarea v-model="content" placeholder="내용" class="form-control" rows="10"></textarea>
      </div>
    </div>


    <div class="text-right mt-3 mb-3">
      <button @click="showModal=true" class="btn btn-success">메일보내기</button>
    </div>

    <Modal v-if="isSend">
      <h3 slot="header">보내기</h3>
      <div slot="body">
        정말 메일을 보내시겠습니까?
      </div>
      <span slot="footer" @click="showModal=false">
      <button @click="onSend" class="btn btn-sm btn-warning">보내기</button>
      <button class="btn btn-sm btn-warning">취소하기</button>
    </span>
    </Modal>

  </div>

</template>

<script>

  import {mapActions} from 'vuex'
  import Modal from '../../Modal'
  import Sidebar from '../Sidebar'

  export default {
    data() {
      return {
        showModal: false,
        title: '',
        content: ''
      }
    },
    computed: {
      isSend() {
        return this.showModal
      }
    },
    components: {
      Modal,
      Sidebar
    },
    methods: {
      ...mapActions([
        'SEND'
      ]),
      onSend() {
        this.SEND({title: this.title, content: this.content})
        this.title = ''
        this.content = ''
      }
    }
  }
</script>

<style scoped>

</style>
