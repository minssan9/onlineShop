<template>

  <div>
    <div v-if="isCheck" class="row ml-auto mt-1">
      <img src="../../../assets/person.png"/>
      <div class="btn btn-sm btn-light">
        <p v-if="message">{{message.senderName}}:{{message.sendMessage}}</p>
      </div>
    </div>
    <div v-else class="row mr-auto mt-1 justify-content-end">
      <img src="../../../assets/person.png"/>
      <div class="btn btn-sm btn-info">
        <p v-if="message">{{message.senderName}}:{{message.sendMessage}}</p>
      </div>
    </div>
  </div>

</template>

<script>

  import {mapState} from 'vuex'

  export default {
    props: ['message'],
    computed: {

      ...mapState([
        'account'
      ]),
      isCheck() {
        if (this.account.accountId === this.message.senderName)
          return false
        return true
      },
    }
  }


</script>

<style scoped>

</style>
