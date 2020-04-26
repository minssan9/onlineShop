<template>

  <div class="container mt-5 mb-5" style="max-width: 500px">
    <div class="form-group">
      <label for="userId">사용자 아이디</label>
      <p class="form-control" id="userId" name="userId" placeholder="User ID">{{account.accountId}}</p>
    </div>
    <div class="form-group">
      <label for="password">비밀번호</label>
      <input v-model="updateAccount.userPassword" type="password" class="form-control" id="password" name="password"
             placeholder="비밀번호">
    </div>
    <div class="form-group">
      <label for="name">이름</label>
      <input v-model="updateAccount.name" type="text" class="form-control" id="name" name="name" placeholder="이름">
    </div>
    <div class="form-group">
      <label for="name">주소</label>
      <input v-model="updateAccount.address" type="text" class="form-control" name="address" placeholder="주소">
    </div>
    <div class="form-group">
      <label for="name">전화번호</label>
      <input v-model="updateAccount.phone" type="text" class="form-control" name="phone" placeholder="전화번호">
    </div>
    <button @click="onUpdate" type="submit" class="btn btn-outline-success">수정하기</button>
  </div>
</template>

<script>
  import {mapState} from 'vuex'
  import {mapActions} from 'vuex'

  export default {
    data() {
      return {
        updateAccount: {
          userPassword: '',
          name: '',
          address: '',
          phone:''
        }
      }
    },
    methods: {
      ...mapActions([
        'FETCH_ACCOUNT',
        'UPDATE_ACCOUNT'
      ]),
      onUpdate() {
        this.UPDATE_ACCOUNT(this.updateAccount)
      }
    },
    computed: {
      ...mapState([
        'account'
      ])
    },
    created() {
      this.FETCH_ACCOUNT()
    }
  }
</script>

<style scoped>
</style>
