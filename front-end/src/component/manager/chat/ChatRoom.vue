<template>

  <div class="row">


      <div class="card col-4 " style="overflow:scroll;">
        <h5 class="text-center mb-2 mt-2">채팅 목록</h5>
        <ul @click="onChatClick" class="list-group list-group-flush">

          <router-link v-if="!isManager(chat.account)":to="`/manager/chat/${chat.id}`" v-for="(chat,index) in chatList" :key="index"
                       class="btn btn-outline-info list-group-item">
            <div class="text-left">
              <h5 class="text-dark">{{chat.account.accountId}}</h5>
              <span v-if="chat.unRead>0" class="badge badge-danger float-right">{{chat.unRead}}</span>
              <p v-if="chat.messages[chat.messages.length-1]" class="text-dark ">
                {{chat.messages[chat.messages.length-1].sendMessage}} <span class="mt-1 float-right badge btn-light">{{chat.messages[chat.messages.length-1].sendDateTime}}</span>
              </p>
            </div>
          </router-link>
        </ul>
      </div>

      <div v-if="this.$route.params.id" class="col-8">
        <pre id="scroll" class="pre-scrollable" style="min-height:340px">
          <ChatText v-for="(message,index) in chat" :key="index" :message="message"></ChatText>
        </pre>
        <form class="mt-3 row justify-content-end">
          <div class="col-9">
            <input v-model="send_message" type="text" placeholder="Message..." class="form-control" autofocus/>
          </div>
          <div class="col-3">
            <button type="submit" @click.prevent="send" class="btn btn-info btn-block">Send</button>
          </div>
        </form>
      </div>

      <div v-else class="text-center m-5">
        <h5> 채팅할 상대방을 눌러주세요.</h5>
      </div>
    </div>

  </div>
</template>

<script>
  import ChatText from "./ChatText"
  import SockJS from 'sockjs-client'
  import Stomp from 'webstomp-client'
  import {mapState, mapMutations, mapActions} from 'vuex'

  export default {
    data() {
      return {
        userId: '관리자',
        send_message: '',
        connected: false
      }
    },
    components: {
      ChatText
    },
    computed: {
      ...mapState([
        'account',
        'chat',
        'chatList'
      ])
    },
    methods: {
      ...mapActions([
        'FETCH_MANAGER_CHAT_LIST',
        'FETCH_MANAGER_CHAT',
        'READ'
      ]),
      ...mapMutations([
        'PUSH_MESSAGE'
      ]),
      scrollBottomFix() {
        var container = this.$el.querySelector("#scroll");
        container.scrollTop = container.scrollHeight;
      },
      connect() {
        this.socket = new SockJS('http://localhost:8090/websockethandler')
        this.stompClient = Stomp.over(this.socket)


        this.stompClient.connect({}, () => {
          this.connected = true
          // 관리자가 특정 회원에게 메세지 전달
          this.stompClient.subscribe(`/queue/board/${this.$route.params.id}`, (tick) => {

            this.PUSH_MESSAGE({
              sendMessage: JSON.parse(tick.body).sendMessage,
              senderName: JSON.parse(tick.body).senderName,
              accountId: JSON.parse(tick.body).accountId,
            })

            this.FETCH_MANAGER_CHAT_LIST()
          })
        }, (error) => {
          this.connected = false
        })
      },

      send() {
        if (this.stompClient && this.stompClient.connected) {
          this.stompClient.send(`/app/question/${this.$route.params.id}`, JSON.stringify({
            'sendMessage': this.send_message,
            'senderName': this.userId,
            'accountId': this.account.id
          }), {})
          this.send_message = ''
          this.scrollBottomFix()
        }
      },
      disconnect() {
        if (this.stompClient) {
          this.stompClient.disconnect()
        }
        this.FETCH_MANAGER_CHAT_LIST()
        this.connected = false
      },
      onChatClick() {
        this.disconnect()
        this.connect()

        this.FETCH_MANAGER_CHAT_LIST()
        this.FETCH_MANAGER_CHAT({id: this.$route.params.id})
        this.READ({id: this.$route.params.id})
        this.scrollBottomFix()
      },
      isManager(account){
        if(!account.roles)
          return false

        if(!account.roles.includes('ADMIN'))
          return false

        return true
      }
    },
    created() {
      this.connect()
      this.FETCH_MANAGER_CHAT_LIST()
    },
    beforeDestroy() {
      this.disconnect()
    }
  }
</script>

<style scoped>

</style>
