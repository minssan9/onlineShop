<template>
  <div class="col-4 col-lg-3 text-center mb-5">
    <div class="card-item card">
      <router-link :to="`/item/${item.id}`">
        <img class="card-img-top" :src="`http://localhost:8090/api/items/download?fileName=${this.item.itemFileList[0].uuid}_${this.item.itemFileList[0].fileName}`" alt="Card image cap">
      </router-link>
      <div class="card-body">
        <p class="card-title t1">
          <strong>{{item.title}}</strong>
        </p>
        <p class="card-text"><s>{{item.price}}원</s> <strong> {{total}}원</strong></p>
        <span class="badge-md badge badge-danger"> BEST </span> <span
        class="badge-md badge badge-primary"> 주문폭주 </span> <span
        class="badge-md badge badge-warning"> 총알배송 </span>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    props: ['item'],
    data () {
      return {
      }
    },
    computed:{
      total () {
        return this.item.price * 1 - this.item.discount * 1
      }
    }
  }
</script>

<style scoped>

</style>
