<template>
  <ul v-if="qnaList.totalPages>0" class="pagination justify-content-center">
    <li v-if="pagination!=0" class="page-item" @click="onPrevious">
      <button class="page-link">이전</button>
    </li>

    <li v-if="qnaList.totalPages-(pagination*10)>=10" v-for="i in 10" :key="i" class="page-item">
      <button v-if="search.mode===true" @click.stop="onSearchFetch(i+(pagination*10))" class="page-link">{{i+(pagination*10)}}
      </button>
      <button v-else @click="onFetch(i+(pagination*10))" class="page-link">{{i+(pagination*10)}}</button>
    </li>

    <li v-if="qnaList.totalPages-(pagination*10)<10" v-for="i in (qnaList.totalPages%10)" :key="i"
        class="page-item">
      <button v-if="search.mode===true" @click.stop="onSearchFetch(i+(pagination*10))" class="page-link">{{i+(pagination*10)}}
      </button>
      <button v-else @click="onFetch(i+(pagination*10))" class="page-link">{{i+(pagination*10)}}</button>
    </li>

    <li v-if="qnaList.totalPages>=11 && (pagination+1)*10<qnaList.totalPages" class="page-item"
        @click="onNext">
      <button class="page-link">다음</button>
    </li>
  </ul>
</template>

<script>
  import {mapState,mapActions} from 'vuex'

  export default {
    data() {
      return {
        pagination: 0
      }
    },
    computed: {
      ...mapState([
        'qnaList',
        'search'
      ])
    },
    methods: {
      ...mapActions([
        'FETCH_MANAGER_QNA',
        'FETCH_QNA_MANAGER_SEARCH'
      ]),
      onPrevious() {
        if (this.pagination === 0)
          return
        this.pagination -= 1
      },
      onNext() {
        this.pagination += 1
      },
      onFetch(page) {
        this.FETCH_MANAGER_QNA({page: page - 1})
      },
      onSearchFetch(page) {
        this.FETCH_QNA_MANAGER_SEARCH({page: page - 1, keyword: this.search.keyword, option: this.search.option})
      }
    }
  }
</script>

<style scoped>

</style>
