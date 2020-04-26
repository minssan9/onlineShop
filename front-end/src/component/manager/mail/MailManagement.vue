<template>
  <div class="row">

    <Sidebar></Sidebar>

    <div class="col-10 mt-5 mx-auto">
      <div class="card">
        <div class="card-header">
          <h6 class="text-primary font-weight-bold">메일보내기</h6>
        </div>
        <div class="card-body">
          <MailForm/>
        </div>
      </div>
    </div>
  </div>

</template>


<script>
  import Sidebar from '../Sidebar'
  import MailForm from './MailForm'

  export default {
    components: {
      Sidebar,
      MailForm
    }
  }
</script>

<style scoped>

</style>
