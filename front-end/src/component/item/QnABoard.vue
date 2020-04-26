<template>
  <div class="mb-3">
    <div class="row">

      <td class="col-2"><p>{{qna.id}}</p></td>
      <td class="col-4"><p @click="onToggle" class="btn btn-outline-primary">{{qna.title}}</p></td>
      <td class="col-2"><p>{{qna.writer}}</p></td>
      <td class="col-2"><p>{{qna.created}}</p></td>
      <td v-if="qna.status===0" class="col-2"><span class="badge badge-light text-warning ">답변대기중</span></td>
      <td v-else class="col-2"><span class="badge badge-light text-danger ">답변완료</span></td>
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
        <p v-if="qna.status===0" class="col-11">답변대기중 입니다.</p>
        <p v-else class="col-11">{{qna.answer}}</p>

      </div>
    </div>

  </div>
</template>

<script>
  export default {
    props: ['qna'],

    data () {
      return {
        showAnswer: false
      }
    },
    computed: {
      isShow () {
        return this.showAnswer
      }
    },
    methods: {
      onToggle () {
        this.showAnswer = !this.showAnswer
      }
    }
  }
</script>

<style scoped>

</style>
