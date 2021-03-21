<template>
  <div class="hello">
    <h1>{{ message }}</h1>
  </div>
</template>

<script>
import { getHelloWorld } from '../services/HelloWorldService'

export default {
  name: 'HelloWorld',
  data() {
    return {
      message: ""
    }
  },
  methods: {
    getHelloWorldFromService() {
      getHelloWorld().then(response => {
        this.message = response.message;
      });
    }
  },
  mounted () {
    this.getHelloWorldFromService();
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  h1 {
    font-size: 300px;
  }
</style>
