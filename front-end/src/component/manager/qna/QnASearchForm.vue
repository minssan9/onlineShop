<template>
  <div class="input-group mb-3 ml-auto mr-auto" style="max-width: 500px">
    <div class="input-group-prepend">
      <button class="btn btn-outline-secondary dropdown-toggle" type="button" data-toggle="dropdown"
              aria-haspopup="true" aria-expanded="false">{{menu}}
      </button>
      <div class="dropdown-menu">
        <a v-for="(data,index) in menus" :key="index" class="dropdown-item" @click="menuChange(data)"
           href="#">{{data}}</a>
      </div>
    </div>
    <input v-model="keyword" type="text" class="form-control" aria-label="Text input with dropdown button"
           placeholder="Search">

    <button class="btn btn-sm btn-outline-secondary" @click="onSubmit">검색</button>
  </div>

</template>

<script>
  import {mapMutations,mapActions} from 'vuex'
  export default {
    data() {
      return {
        menu: '상품명',
        keyword: '',
        menus: ['상품명', '제목', '작성자','답변상태']
      }
    },
    methods: {
      ...mapMutations([
        'SET_SEARCH_MODE'
      ]),
      ...mapActions([
        'FETCH_QNA_MANAGER_SEARCH'
      ]),
      menuChange(menu) {
        this.menu = menu
      },
      onSubmit(){
        let option=this.menus.indexOf(this.menu)

        this.SET_SEARCH_MODE({keyword: this.keyword, option: option})
        this.FETCH_QNA_MANAGER_SEARCH({page: 0, keyword: this.keyword, option: option})
      }
    }
  }
</script>

<style scoped>

</style>
