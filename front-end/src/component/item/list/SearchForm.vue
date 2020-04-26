<template>
  <div class="input-group mb-3 ml-auto mr-auto" style="max-width: 500px">
    <div class="input-group-prepend">
      <button class="btn btn-outline-secondary disabled">제목</button>
    </div>
    <input v-model="keyword" type="text" class="form-control" placeholder="Search">
    <button class="btn btn-sm btn-outline-secondary" @click="onSubmit">검색</button>
  </div>
</template>

<script>
  import {mapMutations,mapActions} from 'vuex'

  export default {

    data() {
      return {
        keyword: '',
      }
    },
    methods: {
      ...mapMutations([
        'SET_SEARCH_MODE'
      ]),
      ...mapActions([
        'FETCH_ITEM_LIST_SEARCH'
      ]),
      onSubmit() {

        this.FETCH_ITEM_LIST_SEARCH({page:0,option:'id',sort:'DESC',keyword:this.keyword})
        this.SET_SEARCH_MODE({page:0,option:'id',sort:'DESC',keyword:this.keyword,mode:true})
        this.keyword=''
        this.$emit('@reset')
      }
    }
  }
</script>

<style scoped>

</style>
