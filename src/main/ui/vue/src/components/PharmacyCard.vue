<template>
    <b-container>
        <div v-for="p in this.pharmacies" :key="p.id">
            
            <b-container class="pt-2 pb-2">
            <b-row class="w-100 hover-shadow pt-2 pb-2 border">
                    <b-col> 
                        <router-link :to="{name: 'PharmacyPreview', params: {id: p.id}}">
                            <img class="pharmacy_img" src="../slika.jpg" alt="">
                        </router-link>
                    </b-col>
                    <b-col lg="8" md="6" sm="4">
                        <b-row>
                            <h1 class="text-left"> {{p.name}} </h1>
                        </b-row>
                        <b-row class="h-50 mb-4" style="max-height:110px">
                            <p class="text-left text"> {{p.description}} </p>
                            
                        </b-row>
                        <b-row>
                            
                            <h5 class="text-left h5"> {{ addressToString(p.address) }} </h5>
                        </b-row>
                    </b-col>
            </b-row>
            </b-container>
            
        </div>
    </b-container>
</template>

<script>

const apiURL = "http://localhost:8080/api/pharmacy";

export default {
    components: {
        
    },
    data() {
      return {
        pharmacies: ''
      }
    },
    created() {
    this.axios.get(apiURL).then(response => {

      this.pharmacies = response.data;
      console.log(this.pharmacies[0])
    }).catch(error => console.log(error));
  },

  methods: {
      addressToString: function(address) {
          return address.street + " " + address.number + ", " + address.city + " " + address.country;
      }
  } 
}
</script>

<style scoped>
    .pharmacy_img {
        max-height: 20rem;
        max-width: 20rem;
    }

    .text {
        display: block;
        height: 120px;
        overflow: hidden;
        white-space: pre-wrap;
        text-overflow: ellipsis;
    }
</style>