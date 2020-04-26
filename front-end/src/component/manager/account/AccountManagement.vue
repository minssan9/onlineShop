<template>
  <div class="row">
    <Sidebar/>
    <div class="col-10 m-5 mx-auto">
      <div class="card">

        <div class="card-header">
          <h6 class="text-primary font-weight-bold">회원관리</h6>
        </div>
        <div class="card-body">
          <table class="table">
            <thead class="thead-light">
            <tr>
              <th scope="col">#</th>
              <th scope="col">아이디</th>
              <th scope="col">이름</th>
              <th scope="col">주소</th>
              <th scope="col">전화번호</th>
              <th scope="col">이메일</th>
              <th scope="col">Level</th>
              <th scope="col">포인트</th>
            </tr>
            </thead>
            <tbody>
            <AccountDetail v-for="account in accountList.content" :key="account.id" :account="account"/>
            </tbody>

          </table>

          <div class="m-5 text-center" v-if="!accountList">
            검색 결과가 없습니다.
          </div>

          <AccountPagination/>
          <AccountSearchForm/>

        </div>
      </div>

    </div>
  </div>
</template>

<script>
  import Sidebar from '../Sidebar'
  import AccountDetail from './AccountDetail'
  import AccountSearchForm from '../account/AccountSearchForm'
  import AccountPagination from '../account/AccountPagination'
  import {mapState, mapMutations} from 'vuex'

  export default {
    components: {
      Sidebar,
      AccountDetail,
      AccountSearchForm,
      AccountPagination
    },
    methods: {
      ...mapMutations([
        'CLEAR_SEARCH_MODE'
      ]),
    },
    computed: {
      ...mapState([
        'accountList',
      ])
    },
    created() {
      this.CLEAR_SEARCH_MODE()
    }
  }
</script>

<style scoped>

</style>
