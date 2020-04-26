<template>
  <div id="carouselExampleInterval" class="carousel slide" data-ride="carousel">
    <div class="carousel-inner">
      <div class="carousel-item active" data-interval="10000">
        <div class="reponsive" style="background-image: url('../../assets/cd.jpg')">
        </div>
      </div>
      <div class="carousel-item" data-interval="2000">
        <div class="reponsive" style="background-image: url('../../assets/ee.jpg')">
        </div>
      </div>
      <div class="carousel-item">
        <div class="reponsive" style="background-image: url('../../assets/aaa.jpg')">
        </div>
      </div>
    </div>
    <a class="carousel-control-prev col-md-5" href="#carouselExampleInterval" role="button" data-slide="prev">
      <span class="carousel-control-prev-icon" aria-hidden="true"></span>
      <span class="sr-only">Previous</span>
    </a>
    <a class="carousel-control-next" href="#carouselExampleInterval" role="button" data-slide="next">
      <span class="carousel-control-next-icon" aria-hidden="true"></span>
      <span class="sr-only">Next</span>
    </a>
  </div>
</template>

<script>
    export default {
        name: "Carousel"
    }
</script>

<style scoped>

</style>
