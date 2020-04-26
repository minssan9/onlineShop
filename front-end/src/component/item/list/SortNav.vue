<template>
  <div class="container mt-5">
    <ul class="list-inline text-right">
      <a href="#" @click="onChangeItemOption(0,'id','DESC')" class="p-0 m-0 btn list-inline-item "><a class="text-dark">신상품순 </a></a>
      <a href="#" @click="onChangeItemOption(0,'sellCount','DESC')" class="p-0 m-0 btn list-inline-item "><a
        class="text-dark">
        판매인기순 </a></a>
      <a href="#" @click="onChangeItemOption(0,'price','ASC')" class="p-0 m-0 btn list-inline-item "><a
        class="text-dark">
        낮은가격순 </a></a>
      <a href="#" @click="onChangeItemOption(0,'price','DESC')" class="p-0 m-0 btn list-inline-item "><a
        class="text-dark">
        높은가격순 </a></a>
    </ul>
    <hr class="divider" style="background: #1d2124">
  </div>
</template>

<script>
  import {mapState, mapMutations, mapActions} from 'vuex'

  export default {
    computed: {
      ...mapState([
        'search'
      ])
    },
    methods: {
      ...mapMutations([
        'SET_SEARCH_MODE'
      ]),
      ...mapActions([
        'FETCH_ITEM_LIST'
      ]),
      onChangeItemOption(page, option, sort) {
        this.SET_SEARCH_MODE({option: option, sort: sort, page: page, mode: false, keyword: this.search.keyword})
        this.FETCH_ITEM_LIST({page: page, size: 16, option: option, sort: sort})

      }
    }
  }
</script>

<style scoped>

</style>
