<template>
  <div>
    <div class="row mt-2">
      <div class="col-1">
        <router-link :to="`/item/${review.itemId}`">{{review.itemTitle}}</router-link>
      </div>
      <div class="col-1">
        <p>{{review.account.accountId}}</p>
      </div>
      <div class="col-7">
        <div class="row">
          <div class="col-3">
            <img style="max-width: 75px"
                 :src="`http://localhost:8090/api/reviews/download?fileName=${review.reviewFile.uuid}_${review.reviewFile.fileName}`"/>
          </div>
          <div class="col-1"></div>
          <div class="col-8">
            <p>{{review.content}}</p>
          </div>
        </div>
      </div>
      <div class="col-2">
        <p>{{review.created}}</p>
      </div>
      <div class="col-1">
        <p>{{review.likeStatus}}</p>
      </div>
    </div>
    <hr/>
  </div>
</template>

<script>
  export default {

    props: ['review']

  }
</script>

<style scoped>

</style>
