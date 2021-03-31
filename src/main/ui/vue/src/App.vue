<template>
  <div id="app">
    <router-view></router-view>
    <footer class="footer">
    <Footer />
    </footer>
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

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}

 
</style>
