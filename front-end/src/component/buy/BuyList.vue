<template>
  <div class="container">
    <h5> 주문할 상품</h5>

    <table class="table">
      <thead class="thead-light">
      <tr>
        <th>구입 상품명</th>
        <th>옵션</th>
        <th>수량</th>
        <th>정가</th>
        <th>할인가</th>
        <th>적립</th>
        <th>삭제</th>
      </tr>
      </thead>
      <tbody>

      <tr v-for="(item,index) in buyList" :key="index">
        <th scope="row">
          <div class="row">
            <div class="col-sm-auto">
              <img class="img-fluid" style="max-width: 75px" :src="`http://localhost:8090/api/items/download?fileName=${item.itemFileList[0].uuid}_${item.itemFileList[0].fileName}`"  alt="img">
            </div>
            <div class="col-sm-auto">
              <p>{{item.title}}</p>
            </div>
          </div>
        </th>
        <td v-if="item.options && item.options[0].option!=='선택'">{{item.options[0].option}}</td>
        <td v-else>없음</td>
        <td>1개</td>
        <td>{{item.price}}원</td>
        <td>{{item.discount}}원</td>
        <td>{{item.savings}}원</td>
        <td>
          <button @click="removeBuy(item.id)" class="btn btn-sm btn-dark">삭제</button>
        </td>
      </tr>

      </tbody>
    </table>
    <p class="text-right">정가:<strong>{{price}}</strong>원</p>
    <p class="text-right">할인가:<strong>{{discount}}</strong>원</p>
    <p class="text-right mt-2">합계:<strong>{{total}}</strong>원</p>
    <hr>
  </div>

</template>

<script>
  import {mapState, mapMutations} from 'vuex'

  export default {

    computed: {
      ...mapState([
        'buyList'
      ]),
      total () {
        let sum = 0
        for (let i = 0; i < this.buyList.length; i++) {
          sum += this.buyList[i].price * 1 - this.buyList[i].discount * 1
        }
        return sum
      },
      discount () {
        let sum = 0
        for (let i = 0; i < this.buyList.length; i++) {
          sum += this.buyList[i].discount * 1
        }
        return sum
      },
      price () {
        let sum = 0
        for (let i = 0; i < this.buyList.length; i++) {
          sum += this.buyList[i].price * 1
        }
        return sum
      }
    },
    methods: {
      ...mapMutations([
        'REMOVE_BUY_LIST'
      ]),
      removeBuy (data) {
        this.REMOVE_BUY_LIST(data)
      }
    }
  }
</script>

<style scoped>

</style>
