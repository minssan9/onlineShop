<template>
  <div>
    <table class="table" style="text-align: center; border: 1px solid #dddddd">
      <tbody>
      <td>
        <div class="row">
          <strong class="col-6 text-left">{{review.likeStatus}}</strong>
          <span class="col-6 text-right">{{review.account.name}} {{review.created}}</span>
        </div>
        <hr/>

        <div class="row mt-3">
          <div class="col-3">
            <Carousel :itemFile="review.reviewFile"></Carousel>
            <img style="max-width: 100px"
                 :src="`http://localhost:8090/api/reviews/download?fileName=${review.reviewFile.uuid}_${review.reviewFile.fileName}`"/>
          </div>
          <p class="col-9"> {{review.content}}</p>
        </div>
      </td>
      </tbody>
    </table>
  </div>
</template>

<script>
  import Carousel from './Carousel'
  export default {

    props: ['review'],

    components:{
      Carousel
    }

  }
</script>

<style scoped>

</style>
