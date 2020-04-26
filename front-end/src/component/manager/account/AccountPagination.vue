<template>
  <ul v-if="accountList.totalPages>0" class="pagination justify-content-center">
    <li v-if="pagination!=0" class="page-item" @click="onPrevious">
      <button class="page-link">이전</button>
    </li>

    <li v-if="accountList.totalPages-(pagination*10)>=10" v-for="i in 10" :key="i" class="page-item">
      <button v-if="search.mode===true" @click.stop="onSearchFetch(i+(pagination*10))" class="page-link">{{i+(pagination*10)}}
      </button>
      <button v-else @click="onFetch(i+(pagination*10))" class="page-link">{{i+(pagination*10)}}</button>
    </li>

    <li v-if="accountList.totalPages-(pagination*10)<10" v-for="i in (accountList.totalPages%10)" :key="i"
        class="page-item">
      <button v-if="search.mode===true" @click.stop="onSearchFetch(i+(pagination*10))" class="page-link">{{i+(pagination*10)}}
      </button>
      <button v-else @click="onFetch(i+(pagination*10))" class="page-link">{{i+(pagination*10)}}</button>
    </li>

    <li v-if="accountList.totalPages>=11 && (pagination+1)*10<accountList.totalPages" class="page-item"
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
        'accountList',
        'search'
      ])
    },
    methods: {
      ...mapActions([
        'FETCH_MANAGER_ACCOUNT',
        'FETCH_MANAGER_ACCOUNT_SEARCH'
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
        this.FETCH_MANAGER_ACCOUNT({page: page - 1})
      },
      onSearchFetch(page) {
        this.FETCH_MANAGER_ACCOUNT_SEARCH({page: page - 1, keyword: this.search.keyword, option: this.search.option})
      }
    },
    created() {
      this.FETCH_MANAGER_ACCOUNT({page: 0})
    }
  }
</script>

<style scoped>

</style>
