<template>
  <div class="row">
    <svg class="mx-auto" id="graph"></svg>
  </div>
</template>

<script>

  import {mapState, mapActions} from 'vuex'

  export default {
    data() {
      return {
        date: [],
        sales: []
      }
    },
    computed: {
      ...mapState([
        'operationStatus'
      ])
    },
    methods: {
      ...mapActions([
        'FETCH_OPERATION_STATUS'
      ])
    },
    created() {
      this.FETCH_OPERATION_STATUS

      this.operationStatus.forEach(r => {
        this.date.push(r.today)
        this.sales.push(r.sales)
      })
    },
    mounted() {

      const max = this.sales.reduce(function (previous, current) {
        return previous > current ? previous : current;
      });
      const offsetX = 80;
      const offsetY = 20;
      const svgHeight = 500
      const y_range_limit = 450;
      const interval = 5;

      const barElements = d3.select("#graph")
        .selectAll("#bar")
        .data(this.sales)

      barElements.enter()
        .append("rect")
        .attr("class", "bar")
        .attr("height", function (d) {
          return (y_range_limit * d) / max
          // max로 비례식 max:y_range_limit(height)=d:x
        })
        .attr("width", 20)
        .attr("x", function (d, i) {
          return (i * 80) + 50 + offsetX;
          // 80 => 막대기의 간격
        })
        .attr("y", function (d) {
          let bar = (y_range_limit * d) / max
          return (svgHeight - bar - offsetY);
        })

      const textElements = d3.select("#graph")
        .selectAll("#barNum")
        .data(this.sales)

      textElements.enter()
        .append("text")
        .attr("class", "barNum")
        .attr("x", function (d, i) {
          return (i * 80) + 50 + offsetX + 10;
        })
        .attr("y", svgHeight - 5 - offsetY)
        .text(function (d) {
          return d.toLocaleString() + "원";
        })

      //가로방향 선을 표시
      d3.select("#graph").append("rect")
        .attr("class", "axis_x")
        .attr("width", 750)
        .attr("height", 1)
        .attr("transform", "translate(" + offsetX + ", " + ((svgHeight) - offsetY) + ")")

      const xElements = d3.select("#graph")
        .selectAll("#barName")
        .data(this.date)

      xElements.enter()
        .append("text")
        .attr("class", "barName")
        .attr("x", function (d, i) {
          return (i * 80) + 50 + interval + offsetX
        })
        .attr("y", svgHeight + 15 - offsetY)
        .text(function (d, i) {
          return d;
        })

      // 원단위 절사 2222원 => 2000원
      let temp = max + ''
      let maxFloor = temp.charAt(0)
      for (let i = 1; i < temp.length; i++) {
        maxFloor += '0'
      }

      const y = d3.scaleLinear()
        .range([y_range_limit, 0])   // y_range_limit 만큼 domain을 표시하겠다.
        .domain([0, max])            // [a,b] 만큼 넣겠다
      // 데이터중에 가장 큰값을 찾아서 도메인에 넣는다.
      const yScale = d3.axisLeft(y)
        .tickValues(d3.range(0, max + 1, maxFloor / 5))
        .tickFormat(function (d) {
          return d.toLocaleString() + "원"
        })

      d3.select("#graph").append("g")
        .attr("class", "axis")
        .attr("transform", "translate(" + offsetX + ", " + ((svgHeight - y_range_limit) - offsetY) + ")")
        .call(yScale)
    }
  }

</script>

<style scoped>
  svg {
    width: 800px;
    height: 500px;
  }
</style>
