<template>
    <div id="PharmacyListPreview" class="div">
      <button type="button" class="btn btn-light m-auto" align-v="right" @click="filter_show=!filter_show">Filter</button>
      <div v-if="filter_show">
        <PharmacyFilter @childToParent="filterPharmacies"/>
      </div>
      <div v-if="!success">
        <h4>
        </h4>
      </div>
      <div class="ml-4">
        <PharmacyCard :pharmacies="pharmacies"></PharmacyCard>
      </div>
     
    

    </div>    
</template>

<script>
import { defineComponent } from '@vue/composition-api'
import PharmacyCard from '../components/PharmacyCard'
import Search from '../components/Search'
import PharmacyFilter from '../components/PharmacyFilter'


export default defineComponent({
    name: "PharmacyListPreview",
    components: {
        PharmacyCard,
        Search, 
        PharmacyFilter
    },

    props: {
      query: String,
      filter_show: false,
      success: true
    },

    data() {
      return {
        pharmacies: []
      }
    },

    mounted() {
      console.log(this.query);
      if(this.query === 'undefined' || this.query === undefined || this.query === '') {
        this.axios.get(`/api/pharmacy`,{
          headers: {Authorization: "Bearer " + localStorage.getItem('token')}
        })
          .then(response => {
              this.pharmacies = response.data;
          }).catch(error => console.log(error));
      } else {
        console.log(this.query);
        this.axios.get(`/api/pharmacy/search/${this.query}`,{
          headers: {Authorization: "Bearer " + localStorage.getItem('token')}
        })
        .then(response => {
            this.pharmacies = response.data;
            console.log(this.pharmacies[0].name)
        }).catch(error => console.log(error));
      }
    },

  methods: {
    onChildClick(value) {
      console.log(value);
      this.axios.get(`/api/pharmacy/search/${value}`,{
          headers: {Authorization: "Bearer " + localStorage.getItem('token')}
        })
        .then(response => {
            this.pharmacies = response.data;
            console.log(this.pharmacies[0].name)
        }).catch(error => console.log(error));
    },

    filterPharmacies(value) {
      if(value.length !== 0) 
      {
        this.pharmacies = value;
      } else {
        this.axios.get(`http://localhost:8080/api/pharmacy`,{
          headers: {Authorization: "Bearer " + localStorage.getItem('token')}
        })
          .then(response => {
              this.pharmacies = response.data;
          }).catch(error => console.log(error));
      }
      console.log(value);
      console.log(this.pharmacies);
    }
  }

    
    
})

</script>

<style scoped>
  .div {
    min-height: 25rem;
  }

  .btn {
    width: 75%;
  }
</style>
