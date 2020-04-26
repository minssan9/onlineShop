<template>
  <div>
    <div v-for="item in itemList.content" :key="item.id">
      <div class="row mt-2">
        <div class="col-6">
          <div class="row">
            <div class="col-3">
              <img style="max-width: 100px"
                   :src="`http://localhost:8090/api/items/download?fileName=${item.itemFileList[0].uuid}_${item.itemFileList[0].fileName}`"/>
            </div>
            <div class="col-1"></div>
            <div class="col-8">
              <p>{{item.title}}</p>
            </div>
          </div>
        </div>

        <div class="col-3">
          {{item.created}}
        </div>
        <div class="col-3">
          <router-link :to="`/manager/product/update/${item.id}`" class="btn btn-sm btn-primary mr-1">수정</router-link>
          <router-link to="" class="btn btn-sm btn-warning">상세보기</router-link>
          <button class="btn btn-sm btn-danger" @click="onDelete(item.id)">삭제</button>
        </div>
      </div>
      <hr/>
    </div>

    <ul v-if="itemList.totalPages>0" class="pagination justify-content-center">
      <li class="page-item" @click="onPrevious">
        <button class="page-link">이전</button>
      </li>

      <li v-if="itemList.totalPages-(pagination*10)>=10" v-for="i in 10" :key="i" class="page-item">
        <button @click="onFetch(i+(pagination*10))" class="page-link">{{i+(pagination*10)}}</button>
      </li>

      <li v-if="itemList.totalPages-(pagination*10)<10" v-for="i in (itemList.totalPages%10)" :key="i"
          class="page-item">
        <button @click="onFetch(i+(pagination*10))" class="page-link">{{i+(pagination*10)}}</button>
      </li>

      <li v-if="itemList.totalPages>=11 && (pagination+1)*10<itemList.totalPages" class="page-item" @click="onNext">
        <button class="page-link">다음</button>
      </li>
    </ul>

  </div>
</template>

<script>
  import {mapState, mapActions} from 'vuex'

  export default {
    data() {
      return {
        pagination: 0
      }
    },
    computed: {
      ...mapState([
        'itemList',
        'itemOption'
      ])
    },
    methods: {
      ...mapActions([
        'FETCH_ITEM_LIST',
        'DELETE_ITEM'
      ]),
      onDelete(id) {
        this.DELETE_ITEM({id: id})
      },
      onPrevious() {
        if (this.pagination === 0)
          return
        this.pagination -= 1
      },
      onNext() {
        this.pagination += 1
      },
      onFetch(page) {
        this.FETCH_ITEM_LIST({page: page - 1, size: 16, option: 'id', sort: 'DESC'})
      }
    },
    created() {
      this.FETCH_ITEM_LIST({page: 0, size: 16, option: 'id', sort: 'DESC'})
    }
  }
</script>

<style scoped>

</style>
