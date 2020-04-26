<template>
  <tr>
    <th scope="row">{{index+1}}</th>
    <td>
      <img class="img-fluid mr-1" style="max-width: 45px"
           :src="`http://localhost:8090/api/items/download?fileName=${cart.item.itemFileList[0].uuid}_${cart.item.itemFileList[0].fileName}`"
           alt="img">
      {{cart.item.title}}
    </td>
    <td><s><strong>{{cart.item.price}}</strong></s> {{cart.item.price-cart.item.discount}} 원</td>
    <td>
      <div class="row">
        <input type="text" class="text-right form-control ml-1 mr-1" style="max-width: 50px">
        <button class="btn btn-sm btn-outline-secondary ml-1 mr-1">+</button>
        <button class="btn btn-sm btn-outline-secondary ml-1 mr-1">-</button>
        <button class="btn btn-sm btn-outline-secondary ml-1 mr-1">수정</button>
      </div>
    </td>
    <td>
      <button @click="onDeleteCart" class="btn btn-sm btn-outline-secondary">삭제</button>
    </td>
  </tr>
</template>

<script>
  import {mapActions} from 'vuex'

  export default {
    props: ['cart', 'index'],

    methods: {
      ...mapActions([
        'DELETE_ACCOUNT_CART'
      ]),
      onDeleteCart() {
        this.DELETE_ACCOUNT_CART({id: this.cart.id})
      }
    }

  }
</script>

<style scoped>

</style>
