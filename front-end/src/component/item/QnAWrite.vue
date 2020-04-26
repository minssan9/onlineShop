<template>
  <div class="list-group-item">
    <p><strong>상품 문의</strong></p>
    <hr class="border-secondary">
    <div class="row">
      <input v-model="title" type="text" class="form-control" placeholder="제목">
      <hr/>
      <textarea v-model="content" class="form-control mt-2" rows="5" placeholder="내용"></textarea>
    </div>
    <div class="text-right">
      <button @click.prevent="onWrite" class="btn btn-primary mt-2">문의작성</button>
    </div>
  </div>
</template>

<script>

  import {mapActions} from 'vuex'

  export default {
    props: ['id'],

    data() {
      return {
        title: '',
        content: '',
      }
    },
    methods: {
      ...mapActions([
        'ADD_QNA',
        'FETCH_QNA'
      ]),
      onWrite() {

        this.ADD_QNA({itemId: this.id, qna: {title: this.title, content: this.content}})

        this.title = ''
        this.content = ''

        alert('작성되었습니다.')
      }
    }
  }
</script>

<style scoped>

</style>
