<template>
  <div class="container mt-5 mb-5">
    <div class="row">
      <div class="col-md-6">
        <Carousel :itemFile="item.itemFileList"/>
      </div>
      <div class="col-md-6">
        <Order :item="item"/>
      </div>
    </div>

    <ItemDetailNav :active="'0'"/>
    <Content :content="item.content"/>

    <ItemDetailNav :active="'1'"/>


    <div v-if="reviewList.totalPages===0 || !reviewList" class="text-center m-5">
      작성된 리뷰가 없습니다.
    </div>
    <Review v-else v-for="review in reviewList.content" :key="review.id" :review="review"/>
    <hr/>
    <ReviewWrite/>

    <ul v-if="reviewList.totalPages>0" class="pagination justify-content-center">
      <li v-if="reviewPagination!=0" @click="onReviewPrevious" class="page-item">
        <button class="page-link">이전</button>
      </li>

      <li v-if="reviewList.totalPages-(reviewPagination*10)>=10" @click="onReviewFetch(i+(reviewPagination*10))"
          v-for="(i,index) in 10" :key="index"
          class="page-item">
        <button class="page-link">{{i+(reviewPagination*10)}}</button>
      </li>

      <li v-if="reviewList.totalPages-(reviewPagination*10)<10" @click="onReviewFetch(i+(reviewPagination*10))"
          v-for="(i,index) in (reviewList.totalPages%10)" :key="index"
          class="page-item">
        <button class="page-link">{{i+(reviewPagination*10)}}</button>
      </li>

      <li v-if="reviewList.totalPages>=11 && (reviewPagination+1)*10<reviewList.totalPages" @click="onReviewNext"
          class="page-item">
        <button class="page-link">다음</button>
      </li>
    </ul>

    <ItemDetailNav :active="'2'"/>

    <div v-if="qnaList.totalPages===0 || !qnaList" class="text-center">
      작성된 문의가 없습니다.
    </div>
    <div v-else class="container table mt-5">
      <div class="thead-light">
        <div class="row">
          <th class="col-2">번호</th>
          <th class="col-4">제목</th>
          <th class="col-2">작성자</th>
          <th class="col-2">작성일</th>
          <th class="col-2">현황</th>
        </div>
        <QnABoard v-for="qna in qnaList.content" :key="qna.id" :qna="qna"/>
      </div>
    </div>

    <ul v-if="qnaList.totalPages>0" class="pagination justify-content-center">
      <li  v-if="qnaPagination!=0" @click="onPrevious" class="page-item">
        <button class="page-link">이전</button>
      </li>

      <li v-if="qnaList.totalPages-(qnaPagination*10)>=10" @click="onFetch(i+(qnaPagination*10))"
          v-for="(i,index) in 10" :key="index"
          class="page-item">
        <button class="page-link">{{i+(qnaPagination*10)}}</button>
      </li>

      <li v-if="qnaList.totalPages-(qnaPagination*10)<10" @click="onFetch(i+(qnaPagination*10))"
          v-for="(i,index) in (qnaList.totalPages%10)" :key="index"
          class="page-item">
        <button class="page-link">{{i+(qnaPagination*10)}}</button>
      </li>

      <li v-if="qnaList.totalPages>=11 && (qnaPagination+1)*10<qnaList.totalPages" @click="onNext" class="page-item">
        <button class="page-link">다음</button>
      </li>
    </ul>

    <QnAWrite class="mt-5" :id="this.$route.params.id"/>

    <ItemDetailNav :active="'3'"/>
    <Etc/>

  </div>


</template>

<script>
  import Carousel from './item/Carousel'
  import Order from './item/Order'
  import ItemDetailNav from './item/ItemDetailNav'
  import Content from './item/Content'
  import ReviewWrite from './item/ReviewWrite'
  import Review from './item/Review'
  import QnABoard from './item/QnABoard'
  import QnAWrite from './item/QnAWrite'
  import Etc from './item/Etc'

  import {mapState, mapActions} from 'vuex'

  export default {
    data() {
      return {
        qnaPagination: 0,
        reviewPagination: 0
      }
    },
    components: {
      Carousel,
      Order,
      ItemDetailNav,
      Content,
      ReviewWrite,
      Review,
      QnABoard,
      QnAWrite,
      Etc
    },
    computed: {
      ...mapState([
        'item',
        'qnaList',
        'reviewList',
      ])
    },
    methods: {
      ...mapActions([
        'FETCH_ITEM',
        'FETCH_QNA',
        'FETCH_REVIEW'
      ]),
      onPrevious() {
        if (this.qnaPagination === 0) {
          return
        }
        this.qnaPagination -= 1
      },
      onNext() {
        this.qnaPagination += 1
      },
      onFetch(index) {
        this.FETCH_QNA({itemId: this.$route.params.id, page: index - 1})
      },
      onReviewPrevious() {
        if (this.reviewPagination === 0) {
          return
        }
        this.reviewPagination -= 1
      },
      onReviewNext() {
        this.reviewPagination += 1
      },
      onReviewFetch(index) {
        this.FETCH_REVIEW({itemId: this.$route.params.id, page: index - 1})
      }
    },
    created() {
      this.FETCH_ITEM(this.$route.params.id)
      this.FETCH_QNA({itemId: this.$route.params.id, page: 0})
      this.FETCH_REVIEW({itemId: this.$route.params.id, page: 0})
    }
  }

</script>

<style scoped>

</style>
