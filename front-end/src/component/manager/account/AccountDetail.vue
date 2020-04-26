<template>

  <tr>
    <th scope="row">{{account.id}}</th>
    <td>{{account.accountId}}</td>
    <td>{{account.name}}</td>
    <td>{{account.address}}</td>
    <td>{{account.phone}}</td>
    <td>{{account.email}}</td>
    <td>{{account.level}}</td>
    <td>{{account.point}}</td>
  </tr>



</template>

<script>

  export default {
    props: ['account']
  }
</script>

<style scoped>

</style>
