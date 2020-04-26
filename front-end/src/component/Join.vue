<template>

  <div class="container list-group-item mt-5 mb-5" style="max-width: 500px">

    <p><strong>회원가입</strong></p>
    <hr class="border-secondary">


    <form>
      <label><strong>아이디</strong></label>
      <input v-model="accountId" type="text" class="form-control" placeholder="아이디">
      <div class="text-right">

        <span v-if="idNotExist" class="text-success">사용 가능한 아이디 입니다.</span>
        <span v-if="idExist" class="text-danger">중복된 아이디가 있습니다.</span>
        <button @click.prevent="onDoubleCheck" class="btn btn-sm btn-outline-secondary mt-2">중복확인</button>

      </div>

      <label><strong>패스워드</strong></label>
      <input v-model="password" type="password" class="form-control" placeholder="패스워드">

      <label><strong>이름</strong></label>
      <input v-model="name" type="text" class="form-control" placeholder="이름">

      <label><strong>주소</strong></label>
      <input v-model="address" type="text" class="form-control" placeholder="주소">

      <label><strong>전화번호</strong></label>
      <input v-model="phone" type="tel" class="form-control" placeholder="전화번호">

      <label><strong>이메일</strong></label>
      <input v-model="email" type="email" class="form-control" placeholder="이메일">
    </form>

    <div v-if="!isSubmit" class="text-right mt-2">
      <p class="text-danger"> 아이디 중복확인을 해야합니다.</p>
    </div>


    <div class="text-right mt-2">
      <button :disabled="!isSubmit" :class="[{'btn-primary':isSubmit},'btn','btn-sm']" @click="isShowModal=true">등록하기
      </button>
      <router-link class="btn btn-sm btn-primary" to="/">취소하기</router-link>
    </div>

    <Modal v-if="isRegister">
      <h3 slot="header">회원등록</h3>
      <div slot="body">
        정말 등록하시겠습니까?
      </div>
      <span slot="footer" @click="isShowModal=false">
          <button @click="onSubmit" class="btn btn-sm btn-warning">등록하기</button>
          <button class="btn btn-sm btn-warning">취소하기</button>
      </span>
    </Modal>
  </div>

</template>

<script>

  import {mapActions} from 'vuex'
  import Modal from './Modal'

  export default {

    data() {
      return {

        accountId: '',
        password: '',
        name: '',
        address: '',
        email: '',
        phone: '',

        idExist: false,
        idNotExist: false,
        isSubmit: false,
        isShowModal: false
      }
    },
    components: {
      Modal
    },
    computed: {
      isRegister() {
        return this.isShowModal
      }
    },
    watch: {
      accountId: function (newAccountId) {
        this.isSubmit = false
        this.idExist = false
        this.idNotExist = false
      }
    },
    methods: {
      ...mapActions([
        'JOIN_ID_CHECK',
        'ADD_ACCOUNT'
      ]),
      onDoubleCheck() {
        this.JOIN_ID_CHECK({accountId: this.accountId})
          .then(({status}) => {
            if (status == 409) {
              this.idExist = true
            } else if (status == 200) {
              this.isSubmit = true
              this.idNotExist = true
            }
          })
      },
      onSubmit() {

        console.log(this.accountId)
        this.ADD_ACCOUNT({
          accountId: this.accountId,
          password: this.password,
          name: this.name,
          address: this.address,
          email:this.email,
          phone:this.phone
        }).then((status) => {
          if (status == 400)
            alert("중복된 아이디가 존재합니다.")
          else if (status == 200) {
            alert("회원가입이 완료되었습니다.")
            this.$router.push("/")
          }
        })
      }
    }
  }
</script>

<style scoped>

</style>
