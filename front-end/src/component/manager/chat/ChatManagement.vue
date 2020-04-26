<template>
  <div class="row">

    <Sidebar></Sidebar>

    <div class="col-10 mt-5 mx-auto">
      <div class="card">

        <div class="card-header">
          <h6 class="text-primary font-weight-bold">채팅</h6>
        </div>
        <div class="card-body">
          <ChatRoom></ChatRoom>
        </div>
      </div>

    </div>
  </div>

</template>


<script>
  import Sidebar from '../Sidebar'
  import ChatList from "./ChatList"
  import ChatRoom from "./ChatRoom"

  export default {
    components: {
      ChatList,
      Sidebar,
      ChatRoom
    }
  }
</script>

<style scoped>

</style>
