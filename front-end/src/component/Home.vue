<template>
  <div>

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
    <div class="container mt-5">
      <div class="row">
        <Card :link="'/item/list'" :src="'../../assets/card1.jpg'" :text="'NEW'"/>
        <Card :link="'/myPage'" :src="'../../assets/card2.jpg'" :text="'MY PAGE'"/>
        <Card :link="'/cart'" :src="'../../assets/card3.jpg'" :text="'Cart'"/>
        <Card :link="'/serviceCenter'" :src="'../../assets/girl.jpg'" :text="'Service Center'"/>
      </div>

      <hr class="mt-5 mb-0"/>
      <h3 class="mt-2 text-dark font-weight-bold">Products</h3>
      <div class="row">
        <ItemCard v-for="item in itemList.content" :key="item.id" :item="item"/>
      </div>
    </div>
  </div>
</template>

<script>
  import Carousel from './Carousel'
  import Card from './Card'
  import ItemCard from './ItemCard'
  import {mapState, mapMutations, mapActions} from 'vuex'

  export default {
    components: {
      Carousel,
      Card,
      ItemCard
    },
    computed: {
      ...mapState([
        'itemList'
      ])
    },
    methods: {
      ...mapMutations([
        'LOGOUT'
      ]),
      ...mapActions([
        'FETCH_ACCOUNT',
        'FETCH_ITEM_LIST'
      ])
    },
    created() {
      this.FETCH_ACCOUNT()
        .then((status) => {
          if (status === 401)
            this.LOGOUT()
        })
      this.FETCH_ITEM_LIST({page: 0, size: 4, option: 'id', sort: 'DESC'})
    }
  }
</script>

<style scoped>


  .reponsive {
    background-size: cover;
    background-position: center;
    height: 50vh;
  }

  @media screen and (max-width: 992px) {
    .reponsive {
      background-size: cover;
      background-position: center;
      height: 30vh;
    }
  }

</style>
