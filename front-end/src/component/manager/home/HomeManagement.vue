<template>
  <div>
    <div class="row">
      <Sidebar/>
      <SalesStatus/>
    </div>
  </div>
</template>

<script>

  import Sidebar from '../Sidebar'
  import SalesStatus from './SalesStatus'

  export default {
    components: {
      Sidebar,
      SalesStatus,
    }
  }
</script>

<style scoped>

</style>
