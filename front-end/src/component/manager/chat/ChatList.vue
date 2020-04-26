<template>

  <div class="card col-sm-4 " style="overflow:scroll;">
    <h5 class="text-center mb-2 mt-2">채팅 목록</h5>
    <ul class="list-group list-group-flush">
      <router-link :to="`/manager/chat/${chat.id}`" v-for="(chat,index) in chatList" :key="index" class="btn btn-outline-success list-group-item">
        <div class="d-flex w-100 justify-content-between">
          <h5 class="mb-1">{{chat.account.accountId}}</h5>
        </div>
        <p>{{chat.chat[0]}}</p>
      </router-link>

    </ul>
  </div>

</template>

<script>

  import {mapActions, mapState} from 'vuex'

  export default {

    methods: {
      ...mapActions([
        'FETCH_MANAGER_CHAT_LIST',
      ]),
    },
    computed: {
      ...mapState([
        'chatList'
      ])
    },
    mounted() {
      this.FETCH_MANAGER_CHAT_LIST()

    }
  }
</script>

<style scoped>

</style>
