<template>
  <div class="col-9 mx-auto mt-5">
    <div class="card shadow">
      <div class="card-header">
        <div class="row">
          <div class="col-4">
            <h6 class="text-primary font-weight-bold ml-3 mt-3">쇼핑몰 운영현황</h6>
          </div>
          <div class="col-8">
            <div class="row justify-content-end mt-2">
              <div class="form-group mt-1 mr-2">
                {{start}}-{{end}}
              </div>
              <div class="form-group">
                <input v-model="input" id="calendar" class="form-control">
              </div>
              <button @click="onSubmit" class="btn btn-primary btn-sm ml-2 mb-3">1주일 조회</button>
            </div>
          </div>
        </div>
      </div>

      <div class="card-body">

        <table class="table">
          <thead class="bg-secondary">
          <tr class="text-right">
            <th class="text-left" scope="col">현황정보</th>
            <th v-for="(data,index) in operationStatus" :key="index">{{data.today}}</th>
          </tr>
          </thead>
          <tbody class="text-right">

          <tr>
            <th class="text-left" scope="row">매출액(단위:원)</th>
            <td v-for="i in operationStatus">{{i.sales.toLocaleString()}}원</td>
          </tr>
          <tr>
            <th class="text-left" scope="row">결제완료</th>
            <td v-for="i in operationStatus">{{i.paymentCompletedCount}}</td>
          </tr>
          <tr>
            <th class="text-left" scope="row">배송준비</th>
            <td v-for="i in operationStatus">{{i.shippingPreparationCount}}</td>
          </tr>
          <tr>
            <th class="text-left" scope="row">배송중</th>
            <td v-for="i in operationStatus">{{i.shippingCount}}</td>
          </tr>
          <tr>
            <th class="text-left" scope="row">배송완료</th>
            <td v-for="i in operationStatus">{{i.shippingCompleteCount}}</td>
          </tr>
          <tr>
            <th class="text-left" scope="row">상품문의</th>
            <td v-for="i in operationStatus">{{i.qnaCount}}</td>
          <tr>
            <th class="text-left" scope="row">상품후기</th>
            <td v-for="i in operationStatus">{{i.reviewCount}}</td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>

</template>

<script>

  import {mapState, mapActions} from 'vuex'

  export default {
    data() {
      return {
        start: '',
        end: '',
        input: ''
      }
    },
    computed: {
      ...mapState([
        'operationStatus'
      ])
    },
    methods: {
      ...mapActions([
        'FETCH_OPERATION_STATUS',
        'FETCH_OPERATION_STATUS_SEARCH'
      ]),
      onSubmit() {
        if ($('#calendar').val().length == 0) {
          alert('기간을 입력해주세요')
          return
        }

        let split = $('#calendar').val().split("-")

        let yy = split[0]
        let mm = split[1]
        let dd = split[2]

        this.FETCH_OPERATION_STATUS_SEARCH({yy: yy, mm: mm, dd: dd})

        this.start = this.operationStatus[0].today
        this.end = this.operationStatus[6].today
      }
    },
    mounted() {
      $('#calendar').datepicker({
        dateFormat: 'yy-mm-dd',
      })
      this.FETCH_OPERATION_STATUS()
    },
    updated() {
      this.start = this.operationStatus[0].today
      this.end = this.operationStatus[6].today
    }
  }


</script>
