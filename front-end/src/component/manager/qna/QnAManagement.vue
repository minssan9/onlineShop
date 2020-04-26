<template>
  <div class="row">
    <Sidebar/>
    <div class="col-10 mt-5 mx-auto">
      <div class="card">
        <div class="card-header">
          <h6 class="text-primary font-weight-bold">QnA</h6>
        </div>
        <div class="card-body">
          <div class="row">
            <th class="col-2">상품명</th>
            <th class="col-2">제목</th>
            <th class="col-2">작성자</th>
            <th class="col-2">작성일자</th>
            <th class="col-2">답변상태</th>
            <th class="col-2">기타</th>
          </div>

          <hr/>
          <div v-if="!qnaList || qnaList.totalPages===0" class="text-center m-5">
            <p> 검색 결과가 없습니다.</p>
          </div>
          <QnADetail v-for="qna in qnaList.content" :key="qna.id" :qna="qna"/>
          <QnAPagination/>
          <QnASearchForm/>
        </div>
      </div>

    </div>
  </div>

</template>

<script>
  import {mapState, mapActions} from 'vuex'
  import Sidebar from '../Sidebar'
  import QnADetail from '../qna//QnADetail'
  import QnASearchForm from '../qna/QnASearchForm'
  import QnAPagination from '../qna/QnAPagination'

  export default {
    components: {
      QnADetail,
      Sidebar,
      QnASearchForm,
      QnAPagination
    },
    computed: {
      ...mapState([
        'qnaList',
      ]),
    },
    methods: {
      ...mapActions([
        'FETCH_MANAGER_QNA'
      ]),
    },
    created() {
      this.FETCH_MANAGER_QNA({page: 0})
    }
  }
</script>

<style scoped>

</style>
