<template>
    <div id="PharmacyListPreview" class="div">
    <!--    
        <b-navbar fixed="top" toggleable="lg" type="dark" variant="dark">
    <b-navbar-brand href="#">Home Page</b-navbar-brand>
    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
    <b-collapse id="nav-collapse" is-nav>
      <b-navbar-nav>
        <b-nav-item href="#">All Products</b-nav-item>
        <b-nav-item href="#">Contact</b-nav-item>
      </b-navbar-nav>
      
    
      
      <b-navbar-nav class="ml-auto">
        <Search />

        <b-nav-item-dropdown right>
          
          <template #button-content>
            <em class="pl-2">User</em>
          </template>
          <b-dropdown-item href="#">Sign In</b-dropdown-item>
        </b-nav-item-dropdown>
      </b-navbar-nav>
    </b-collapse>
  </b-navbar> -->

    <PharmacyCard :pharmacies="pharmacies"></PharmacyCard>
    

    </div>    
</template>

<script>
import { defineComponent } from '@vue/composition-api'
import PharmacyCard from '../components/PharmacyCard'
import Search from '../components/Search'



export default defineComponent({
    name: "PharmacyListPreview",
    components: {
        PharmacyCard,
        Search
    },

    props: {
      query: String
    },

    data() {
      return {
        pharmacies: []
      }
    },

    mounted() {
      console.log(this.query);
      if(this.query === 'undefined' || this.query === undefined || this.query === '') {
        this.axios.get(`http://localhost:8080/api/pharmacy`)
          .then(response => {
              this.pharmacies = response.data;
          }).catch(error => console.log(error));
      } else {
        console.log(this.query);
        this.axios.get(`http://localhost:8080/api/pharmacy/search/${this.query}`)
        .then(response => {
            this.pharmacies = response.data;
            console.log(this.pharmacies[0].name)
        }).catch(error => console.log(error));
      }
    },

  methods: {
    onChildClick(value) {
      console.log(value);
      this.axios.get(`http://localhost:8080/api/pharmacy/search/${value}`)
        .then(response => {
            this.pharmacies = response.data;
            console.log(this.pharmacies[0].name)
        }).catch(error => console.log(error));
    }
  }

    
    
})

</script>

<style scoped>
  .div {
    min-height: 25rem;
  }
</style>
