<template>
  <div class="container">
    <div class="row mt-5 mb-5">
      <Sidebar/>
      <div class="col-9">
        <h4 class="text-center"> 1:1 문의</h4>
        <hr/>

        <pre id="scroll" class="pre-scrollable" style="min-height:340px ">
        <ChatText v-for="(msg,index) in chat" :key="index" :message="msg"/>
        </pre>
        <form class="row">
          <div class="col-10">
            <input v-model="send_message" type="text" class="form-control" placeholder="입력하기" autofocus>
          </div>
          <div class="col-1">
            <button class="btn btn-outline-success" type="submit" @click.prevent="send()">전송
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
  import ChatText from './ChatText'
  import Sidebar from './Sidebar'
  import SockJs from 'sockjs-client'
  import Stomp from 'webstomp-client'
  import {mapState, mapMutations, mapActions} from 'vuex'

  export default {
    components: {
      ChatText,
      Sidebar
    },
    data() {
      return {
        send_message: '',

        connected: false
      }
    },
    computed: {
      ...mapState([
        'account',
        'chat'
      ])
    },
    methods: {
      ...mapActions([
        'FETCH_MANAGER_CHAT',
      ]),
      ...mapMutations([
        'PUSH_MESSAGE'
      ]),
      scrollBottomFix(){
        let container = this.$el.querySelector("#scroll");
        container.scrollTop = container.scrollHeight;
      },
      connect() {
        this.socket = new SockJs('http://localhost:8090/websockethandler')
        this.stompClient = Stomp.over(this.socket)

        this.stompClient.connect({}, () => {
          this.connected = true
          this.stompClient.subscribe(`/queue/board/${this.$route.params.id}`, (tick) => {
            this.PUSH_MESSAGE({
              sendMessage: JSON.parse(tick.body).sendMessage,
              senderName: JSON.parse(tick.body).senderName,
              accountId: JSON.parse(tick.body).accountId,
            })
            this.scrollBottomFix()
          })
        }, (error) => {
          this.connected = false
        })
      },
      send() {
        if (this.stompClient && this.stompClient.connected) {
          this.stompClient.send(`/app/question/${this.$route.params.id}`, JSON.stringify({
            'sendMessage': this.send_message,
            'senderName': this.account.accountId,
            'accountId':this.account.id
          }), {})
          this.send_message = ''
        }
      },
      disconnect() {
        if (this.stompClient) {
          this.stompClient.disconnect()
        }
        this.connected = false
      },
    },
    created() {
      this.FETCH_MANAGER_CHAT({id: this.$route.params.id})
    },
    mounted() {
      this.connect()
      this.scrollBottomFix()
    },
    beforeDestroy() {
      // 다른페이지로 갔다가 다시올경우 여러번 커넥트 되어있어 여러개 전송됨
      // 그에따라 종료되기전 연결끊어줌
      this.disconnect()
    }
  }
</script>

<style scoped>

</style>
