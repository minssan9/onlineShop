<template>
  <div class="row mb-5">
    <Sidebar/>

    <div class="col-10 mt-5 mx-auto">
      <div class="card">

        <div class="card-header">
          <h6 class="text-primary font-weight-bold">상품명</h6>
        </div>
        <div class="card">
          <div class="p-2">
            <div class="row">
              <th class="col-6">상품명</th>
              <th class="col-3">작성일자</th>
              <th class="col-3">수정</th>
            </div>
            <hr/>
            <ProductList/>
          </div>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
  import Sidebar from '../Sidebar'
  import ProductList from './ProductListDetail'

  export default {

    components: {
      Sidebar,
      ProductList
    }
  }
</script>

<style scoped>

</style>
