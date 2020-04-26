<template>
  <div class="mt-5 mb-5 ml-auto mr-auto" style="max-width: 500px">
    <h2>Login</h2>
    <form @submit.prevent="onSubmit">
      <div>
        <label for="email">Email</label>
        <input class="form-control" type="text" name="email" v-model="username" autofocus
               placeholder="test@test.com"/>
      </div>
      <div>
        <label for="password">Passwrod</label>
        <input class="form-control" type="password" v-model="password" placeholder="password"/>
      </div>
      <div class="text-right m-1">
        <button class="btn" :class="{'btn-success': !invalidForm}" type="submit" :disabled="invalidForm">Log In</button>
      </div>
    </form>
  </div>
</template>

<script>

  export default {
    data() {
      return {
        username: '',
        password: '',
        returnPath: '',
        error: '',
        grant_type: 'password'
      }
    },
    computed: {
      invalidForm() {
        return !this.username || !this.password
      }
    },
    created() {
      this.returnPath = this.$route.query.returnPath || '/'
    },
    methods: {
      onSubmit() {
        this.$store.dispatch('LOGIN', {username:this.username, password:this.password,grant_type:this.grant_type})
          .then(() => {
            this.$router.push(this.returnPath)
          }).catch(err => {
        })
      }
    }
  }
</script>

