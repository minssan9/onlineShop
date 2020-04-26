<template>
  <router-link class="col-6 col-lg-3 mt-5" :to="link">
    <div style="max-width: 30rem">
      <div class="card-img-wrap card-img-caption">
        <h3 class="card-text text-white font-weight-bold ">{{text}}</h3>
        <img :src="src" class="card-img-top">
      </div>
    </div>
  </router-link>
</template>

<script>
  export default {
    props: ['link','src', 'text']
  }
</script>

<style scoped>
  .card-img-caption {
    border-top-left-radius: calc(.25rem - 1px);
    border-top-right-radius: calc(.25rem - 1px);
  }

  .card-img-caption .card-img-top {
    z-index: 0;
  }

  .card-img-caption .card-text {
    text-align: center;
    width: 100%;
    margin: 20% 0;
    position: absolute;
    z-index: 1;
  }

  .card-img-wrap {
    overflow: hidden;
    position: relative;
  }

  .card-img-wrap:after {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: rgba(255, 255, 255, 0.3);
    opacity: 0;
    transition: opacity .25s;
  }

  .card-img-wrap img {
    transition: transform .25s;
    width: 100%;
  }

  .card-img-wrap:hover img {
    transform: scale(1.2);
  }

  .card-img-wrap:hover:after {
    opacity: 1;
  }

</style>
