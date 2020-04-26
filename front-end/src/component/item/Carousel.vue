<template>
  <div>
    <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
      <ol class="carousel-indicators">
        <li v-for="(itemFile,index) in itemFile" :key="itemFile.id" :class="[{'active':index===0}]"
            data-target="#carouselExampleIndicators" :data-slide-to="`${index}`"></li>
      </ol>
      <div class="carousel-inner">
        <div v-for="(itemFile,index) in itemFile" :key="itemFile.id" :class="[{'active':index===0},'carousel-item']">
          <img :src="`http://localhost:8090/api/items/download?fileName=${itemFile.uuid}_${itemFile.fileName}`"
               alt="img">
        </div>
      </div>
      <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="sr-only">Previous</span>
      </a>
      <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="sr-only">Next</span>
      </a>
    </div>
  </div>
</template>

<script>

  export default {

    props: ['itemFile']
  }
</script>

<style scoped>

</style>
