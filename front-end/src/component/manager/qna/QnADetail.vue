<template>

  <div>
    <div class="row">
      <td class="col-2">
        <router-link :to="`/item/${qna.itemId}`">{{qna.itemTitle}}</router-link>
      </td>
      <td class="col-2"><p class="btn btn-outline-primary" @click="onToggle">{{qna.title}}</p></td>
      <td class="col-2">{{qna.writer}}</td>
      <td class="col-2">{{qna.created}}</td>
      <td v-if="qna.status===0" class="col-2"><span class="badge-md badge badge-warning">답변대기</span></td>
      <td v-else class="col-2"><span class="badge-md badge badge-danger">답변완료</span></td>
      <td class="col-2">
        <button class="btn btn-sm btn-outline-danger" @click="onDelete(qna.id)">삭제</button>
      </td>
    </div>

    <div v-if="isShow">
      <hr/>
      <div class="row">
        <strong class="col-1">질문</strong>
        <p class="col-11">{{qna.content}}</p>
      </div>

      <hr/>

      <div class="row">
        <strong class="col-1">답변</strong>
        <div class="col-11">
          <p v-if="qna.status===1">{{qna.answer}}</p>
          <textarea v-model="answer" class="col-11 form-control" rows="5"></textarea>
        </div>

      </div>
      <div class="text-right m-3">
        <button v-if="qna.status===0" class="btn btn-sm btn-primary" @click="onWrite">작성하기</button>
        <button v-else class="btn btn-sm btn-success" @click="onWrite">수정하기</button>
      </div>
    </div>
  </div>
</template>

<script>
  import {mapActions} from 'vuex'

  export default {
    props: ['qna'],

    data() {
      return {
        showQnA: false,
        answer: ''
      }
    },
    computed: {
      isShow() {
        return this.showQnA
      }
    },
    methods: {
      ...mapActions([
        'UPDATE_ANSWER',
        'DELETE_QNA'
      ]),
      onToggle() {
        this.showQnA = !this.showQnA
      },
      onWrite() {
        this.UPDATE_ANSWER({itemId: this.qna.id, content: this.answer})
        this.$router.push('/manager')
      },
      onDelete(id){
        this.DELETE_QNA({qnaId:id})
      }
    }
  }

</script>

<style scoped>

</style>
