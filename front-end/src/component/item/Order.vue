<template>
  <div>
    <form>
      <h4 style="display: inline"> {{item.title}} </h4>
      <span class="badge badge-secondary text-danger mr-1">BEST</span>
      <span class="badge badge-light text-warning ">주문폭주</span>
      <hr class="divider">
      <ul class="list-group">
        <li class="list-inline-item p-3">
          <div class="row">
            <div class="col-sm-4">
              <span><strong>판매가</strong></span>
            </div>
            <div class="col-sm-8">
              <s> {{item.price}}원</s> <span>{{total}}원</span>
              <span>({{item.discount}}원 할인)</span>
            </div>
          </div>
        </li>
        <li class="list-inline-item p-3">
          <div class="row">
            <div class="col-sm-4">
              <span><strong> 적립금 </strong></span>
            </div>
            <div class="col-sm-8">
              <span> {{item.savings}}원 </span>
            </div>
          </div>
        </li>
        <li class="list-inline-item p-3">
          <div class="row">
            <div class="col-sm-4">
              <span><strong> 배송정보</strong> </span>
            </div>
            <div class="col-sm-8">
              <span> 평균배송일 </span>
              <span class="text-primary">{{item.deliveryDate}}일</span>
            </div>
          </div>
        </li>

        <li class="list-inline-item p-3">
          <div class="row">
            <div class="col-lg-3">
              <span> <strong>옵션</strong></span>
            </div>
            <div class="col-lg-9">
              <div class="input-group">
                <select v-model="input" class="custom-select">
                  <option selected>선택</option>
                  <option v-for="(option,index) in item.options" :key="index" :value=option.option>{{option.option}}
                  </option>
                </select>
                <div class="input-group-append">
                  <button @click="onAddOption" class="btn btn-outline-secondary" type="button">추가하기</button>
                </div>
              </div>
            </div>
          </div>
        </li>

        <li class="list-inline-item p-3">
          <div class="row">
            <div class="col-xl-6 p-1 ">
              <router-link to="/buy">
                <button @click="addBuyList" class="btn btn-dark w-100 h-100 p-3">BUY NOW</button>
              </router-link>
            </div>
            <div class="col-xl-6 p-1">
              <button @click.prevent="onAddCart" class="btn btn-light border-secondary w-100 h-100"><img
                src="../../../assets/shopping_basket.png">장바구니
              </button>
            </div>
          </div>
        </li>

        <li class="list-inline-item mt-4">
          <span> 옵션을 선택하시면 아래에 상품이 추가됩니다.</span>
          <hr class="divider">
          <div class="container" style="max-height: 300px">
            <p v-for="(option,index) in options" :key="index">{{option}}
              <button @click.prevent="onDeleteOption(option)" class="float-right btn btn-outline-secondary">삭제</button>
            </p>
          </div>
          <hr class="divider">
        </li>

      </ul>
    </form>
  </div>
</template>

<script>
  import {mapMutations, mapActions} from 'vuex'

  export default {
    props: ['item'],
    data() {
      return {
        input: '선택',
        options: [],
        optionCheck: false
      }
    },
    computed: {
      total() {
        return this.item.price * 1 - this.item.discount * 1
      }
    },
    methods: {
      ...mapMutations([
        'PUSH_BUY_LIST'
      ]),
      ...mapActions([
        'ADD_ACCOUNT_CART'
      ]),
      addBuyList() {
        this.item.options = []
        this.item.options.push({option: this.input})
        console.log(this.item)
        this.PUSH_BUY_LIST(this.item)
        this.onDeleteOption()
      },
      onAddCart() {
        this.ADD_ACCOUNT_CART({itemId: this.item.id})
      },
      onAddOption() {

        if (this.input === '선택') {
          return;
        }

        if (this.options[0] === this.input) {
          alert('이미 선택된 옵션입니다.')
          return
        }
        if (this.optionCheck) {
          alert('이미 추가한 옵션이 있습니다.')
          return
        }

        this.optionCheck = true
        this.options.push(this.input)
      },
      onDeleteOption() {
        this.options = []
        this.optionCheck = false
      }
    }
  }
</script>

<style scoped>

</style>
