<template>
 <!-- #4e73df -->
  <div class="col-2 col-md-1 bg-dark" style="min-height: 750px">
    <ul class="nav navbar-nav">
      <li class="nav-item active mt-2 mb-2">
        <router-link class="nounderline" to="/manager/home">
          <img class="mr-1" src="../../../assets/dashboard.png">
          <span class="text-white">DashBoard</span>
        </router-link>
      </li>
      <li class="nav-item mt-2 mb-2">
        <router-link class="nounderline" to="/manager/order">
          <img class="mr-1" src="../../../assets/receipt.png">
          <span class="text-white">Order</span>
        </router-link>
      </li>
      <li class="nav-item mt-2 mb-2">
        <router-link class="nounderline" to="/manager/product/pick">
          <img class="mr-1" src="../../../assets/cart.png">
          <span class="text-white">Product</span>
        </router-link>
      </li>
      <li class="nav-item mt-2 mb-2">
        <router-link class="nounderline" to="/manager/account">
          <img class="mr-1" src="../../../assets/people.png">
          <span class="text-white">Member</span>
        </router-link>
      </li>
      <li class="nav-item mt-2 mb-2">
        <router-link class="nounderline" to="/manager/review">
          <img class="mr-1" src="../../../assets/review.png">
          <span class="text-white">Review</span>
        </router-link>
      </li>
      <li class="nav-item mt-2 mb-2">
        <router-link class="nounderline" to="/manager/qna">
          <img class="mr-2" src="../../../assets/edit.png">
          <span class="text-white">QnA</span>
        </router-link>
      </li>
      <li class="nav-item mt-2 mb-2">
        <router-link class="nounderline" to="/manager/chat">
          <img class="mr-2" src="../../../assets/chat.png">
          <span class="text-white">Chat</span>
        </router-link>
      </li>
      <li class="nav-item mt-2 mb-2">
        <router-link class="nounderline" to="/manager/mail">
          <img class="mr-2" src="../../../assets/mail.png">
          <span class="text-white">Mail</span>
        </router-link>
      </li>
      <li class="nav-item mt-2 mb-2">
        <router-link class="nounderline" to="/manager/statistics">
          <img class="mr-1" src="../../../assets/chart.png">
          <span class="text-white">Chart</span>
        </router-link>
      </li>

    </ul>
  </div>
</template>

<script>
  export default {
    data() {
      return {}
    }
  }
</script>

<style scoped>

  .nounderline {
    text-decoration: none !important
  }

</style>
