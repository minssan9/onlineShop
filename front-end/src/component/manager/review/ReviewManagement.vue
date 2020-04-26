<template>
  <div class="row">
    <Sidebar/>
    <div class="col-10 mt-5 mx-auto">


      <div class="card">

        <div class="card-header">
          <h6 class="text-primary font-weight-bold">리뷰 관리</h6>
        </div>

        <div class="card-body">
          <div class="row">
            <th class="col-1">상품명</th>
            <th class="col-1">리뷰작성자</th>
            <th class="col-7">작성내용</th>
            <th class="col-2">작성일</th>
            <th class="col-1">평가</th>
          </div>
          <hr/>
          <div v-if="!reviewList || reviewList.totalPages===0" class="m-5 text-center">
            검색 결과가 없습니다.
          </div>
          <Review v-for="review in reviewList.content" :key="review.id" :review="review"/>
          <ReviewPagination/>
          <ReviewSearchForm/>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import Sidebar from '../Sidebar'
  import Review from '../review/Review'
  import ReviewPagination from '../review/ReviewPagination'
  import ReviewSearchForm from '../review/ReviewSearchForm'
  import {mapState, mapActions} from 'vuex'

  export default {
    data() {
      return {
        pagination: 0
      }
    },
    methods: {
      ...mapActions([
        'FETCH_MANAGER_REVIEW'
      ]),
      onFetch(page) {
        this.FETCH_MANAGER_REVIEW({page: page - 1})
      },
      onNext() {
        this.pagination += 1
      },
      onPrevious() {
        if (this.pagination === 0) {
          return
        }

        this.pagination -= 1
      }
    },
    computed: {
      ...mapState([
        'reviewList'
      ])
    },
    components: {
      Sidebar,
      Review,
      ReviewPagination,
      ReviewSearchForm
    },
    created() {
      this.FETCH_MANAGER_REVIEW({page: 0})
    }
  }
</script>

<style scoped>

</style>
