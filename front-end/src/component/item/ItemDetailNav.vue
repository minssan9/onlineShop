<template>
  <div>
    <hr class="mt-5 mb-0" style=" border: 0; height: 1px; background: #ccc;" >
    <div class="container">
      <div class="row text-center">
        <div v-for="(item,index) in items" :class="[{'bg-dark':index===active*1},'col-3','p-4']">
          <span :class="[{'text-white':index===active*1}]">{{item}}</span>
        </div>
      </div>

    </div>
    <hr class="mt-0 mb-5" style=" border: 0; height: 1px; background: #c6c8ca;" >
  </div>
</template>

<script>
  export default {
    props: ['active'],
    data () {
      return {
        items: ['상품 상세정보', '상품 구매후기', '상품 문의', '배송/교환/환불']
      }
    }
  }
</script>

<style scoped>

</style>
