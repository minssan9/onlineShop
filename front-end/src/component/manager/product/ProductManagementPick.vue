<template>
  <div class="row mb-5">
    <Sidebar/>

    <div class="col-10 text-center mt-auto mb-auto">
      <router-link to="/manager/product/register" class="btn btn-lg btn-outline-danger">상품올리기</router-link>
      <router-link to="/manager/product/list" class="btn btn-lg btn-outline-warning">상품목록</router-link>
    </div>
  </div>
</template>

<script>

  import Sidebar from '../Sidebar'
  export default {
    components:{
      Sidebar
    }
  }
</script>

<style scoped>

</style>
