<template>
  <div id="app">
    <Navbar/>
<!--    <CategoryNav/>-->
    <hr class="mb-0 bg-secondary" style="height:2px;"/>
    <router-view></router-view>
    <Footer/>
  </div>
</template>

<script>
  import Navbar from './component/Navbar'
  import Footer from './component/Footer'

  export default {

    data () {
      return {}
    },
    components: {
      Navbar,
      Footer
    }
  }
</script>

<style>

</style>
