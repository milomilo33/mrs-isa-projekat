<template>
  <div class="app">
    <div id="content-wrapper">
      <router-view></router-view>
    </div>
    <Footer />
  </div>
</template>

<script>
import Footer from "./components/Footer.vue"

export default {
  name: 'App',
  components: {
    Footer
  }
}
</script>

<style scoped>
  .app {
    font-family: Avenir, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin-top: 60px;
    position: relative;
    min-height: 100vh;
  }

  #content-wrapper {
    padding-bottom: 18rem;
  }
 
  
</style>
