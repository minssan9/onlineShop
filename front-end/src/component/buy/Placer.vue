<template>
  <div class="container mb-3">
    <h5 class="mb-3"> 주문자 정보</h5>
    <div class="list-group-item">
      <div class="row">
        <div class="col-12">
          <div class="row">
            <div class="col-sm-auto">
              <strong>이름</strong>
            </div>
            <div class="col-sm-auto">
              <p>{{account.name}}</p>
            </div>
          </div>
        </div>
        <div class="col-12">
          <div class="row">
            <div class="col-sm-auto">
              <strong>이메일</strong>
            </div>
            <div class="col-sm-auto">
              <p>{{account.address}}</p>
            </div>
          </div>
        </div>
        <div class="col-12">
          <div class="row">
            <div class="col-sm-auto">
              <strong>연락처</strong>
            </div>
            <div class="col-sm-auto">
              <p>010-0000-0000</p>
            </div>
          </div>
        </div>

      </div>
    </div>
  </div>
</template>

<script>
  export default {
    props: ['account']
  }
</script>

<style scoped>

</style>
