<template>
  <div class="container">
    <!-- 아직 베스트 아이템 기능 준비중 -->
    <!--<div class="row">-->
    <!--<SmallCard/>-->
    <!--</div>-->
    <SortNav/>

    <div class="row">
      <BigCard v-for="item in itemList.content" :key="item.id" :item="item"/>
    </div>


    <SearchForm v-on:@reset="onReset"/>


    <ul v-if="itemList.totalPages>0" class="pagination justify-content-center">
      <li v-if="pagination!=0" class="page-item" @click="onPrevious">
        <button class="page-link">이전</button>
      </li>

      <li v-if="itemList.totalPages-(pagination*10)>10" v-for="(i,index) in 10" :key="index"
          @click="onFetch(i+(pagination*10))"
          class="page-item">
        <button class="page-link">{{i+(pagination*10)}}</button>
      </li>

      <li v-if="itemList.totalPages-(pagination*10)<=10" v-for="i in (itemList.totalPages%10)"
          @click="onFetch(i+(pagination*10))" class="page-item">
        <button class="page-link">{{i+(pagination*10)}}</button>
      </li>

      <li v-if="itemList.totalPages>=11  && (pagination+1)*10<itemList.totalPages" class="page-item" @click="onNext">
        <button class="page-link">다음</button>
      </li>
    </ul>

  </div>

</template>

<script>
  import {mapState, mapMutations, mapActions} from 'vuex'
  import SortNav from './list/SortNav'
  import SmallCard from './list/SmallCard'
  import BigCard from './list/BigCard'
  import SearchForm from './list/SearchForm'

  export default {
    data() {
      return {
        pagination: 0
      }
    },
    components: {
      SmallCard,
      SortNav,
      BigCard,
      SearchForm
    },
    computed: {
      ...mapState([
        'itemList',
        'search'
      ])
    },
    methods: {
      ...mapMutations([
        'SET_SEARCH_MODE'
      ]),
      ...mapActions([
        'FETCH_ITEM_LIST',
        'FETCH_ITEM_LIST_SEARCH'
      ]),
      onNext() {
        this.pagination += 1
      },
      onPrevious() {
        if (this.pagination === 0) {
          return
        }
        this.pagination -= 1
      },
      onFetch(index) {
        if (this.search.mode) {
          this.FETCH_ITEM_LIST_SEARCH({
            page: index - 1,
            option: this.search.option,
            sort: this.search.sort,
            keyword: this.search.keyword
          })
        } else
          this.FETCH_ITEM_LIST({page: index - 1, size: 16, option: this.search.option, sort: this.search.sort})
      },
      onReset() {
        this.pagination = 0
      }

    },
    created() {
      this.SET_SEARCH_MODE({option: 'id', sort: 'DESC', page: 0, mode: false, keyword: ''})
      this.FETCH_ITEM_LIST({page: 0, size: 16, option: 'id', sort: 'DESC'})
    }
  }
</script>

<style scoped>

</style>
