<template>
  <div class="row">
    <Sidebar/>
    <div class="col-10 mt-5 mx-auto">
      <div class="card">
        <div class="card-header">
          <h6 class="font-weight-bold text-primary">쇼핑몰 1주일 운영현황</h6>
        </div>
        <div class="card-body">
          <Chart/>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

  import Sidebar from '../Sidebar'
  import Chart from '../statistics/Chart'

  export default {
    components: {
      Sidebar,
      Chart
    },
  }
</script>

<style scoped>

</style>
