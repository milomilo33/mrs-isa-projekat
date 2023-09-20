<template>
    <div id="PharmacyListPreview" class="div">
      <!-- <b-container>
      <div>
  <b-input-group>
    

    <b-form-input v-model="search" placeholder="Search"></b-form-input>

    <b-input-group-append>
      <b-button variant="outline-primary" @click="doSearch()">Search</b-button>
      <b-button variant="outline-secondary" @click="this.pharmacies = this.all">Clear</b-button>
      
    </b-input-group-append>
  </b-input-group>
  
</div>
      </b-container> -->
      <b-container>
        <div>
        <b-row fluid>
          <b-col cols="8">
            <b-form-input v-model="search" placeholder="Search"></b-form-input>
          </b-col>
          <b-col cols="2">
            <b-button @click="doSearch">Search</b-button>
          </b-col>
          <b-col cols="2">
            <b-button @click="clearAll">Clear</b-button>
          </b-col>
        </b-row>
        </div>
      </b-container>
      <br>
      <button type="button" class="btn btn-light m-auto" @click="filter_show=!filter_show">Filter</button>
      <div v-if="filter_show">
        <PharmacyFilter :allPharmacies="all" @childToParent="filterPharmacies"/>
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

   

    data() {
      return {
        pharmacies: [],
        all: [],
        query: null,
        filter_show: false,
        success: true,
        search: ""
      }
    },

    mounted() {
      if(this.query === null) {
        this.axios.get(process.env.VUE_APP_API_URL + `/pharmacy`,{
          headers: {Authorization: "Bearer " + localStorage.getItem('token')}
        })
          .then(response => {
              this.pharmacies = response.data;
              this.all = response.data;
              console.log("ALL", this.all);
          }).catch(error => console.log(error));
      } else {
        console.log(this.query);
        this.axios.get(process.env.VUE_APP_API_URL + `/pharmacy/search/${this.query}`,{
          headers: {Authorization: "Bearer " + localStorage.getItem('token')}
        })
        .then(response => {
            this.pharmacies = response.data;
            this.all = response.data;
            console.log(this.pharmacies[0].name)
        }).catch(error => console.log(error));
      }
    },

  methods: {
    onChildClick(value) {
      console.log(value);
      this.axios.get(process.env.VUE_APP_API_URL + `/pharmacy/search/${value}`,{
          headers: {Authorization: "Bearer " + localStorage.getItem('token')}
        })
        .then(response => {
            this.pharmacies = response.data;
        }).catch(error => console.log(error));
    },

    filterPharmacies(value) {
      if(value.length !== 0) 
      {
        this.pharmacies = value;
      } else {
        this.axios.get(process.env.VUE_APP_API_URL + `/pharmacy`,{
          headers: {Authorization: "Bearer " + localStorage.getItem('token')}
        })
          .then(response => {
              this.pharmacies = response.data;
          }).catch(error => console.log(error));
      }
      console.log(value);
    },

    doSearch() {
      this.axios.get(`/api/pharmacy/search/${this.search}`,{
          headers: {Authorization: "Bearer " + localStorage.getItem('token')}
        })
        .then(response => {
            this.pharmacies = response.data;
            //this.all = response.data;
            console.log(this.pharmacies[0].name)
        }).catch(error => console.log(error));
    },

    clearAll() {
      this.pharmacies = this.all;
      this.search = "";
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
