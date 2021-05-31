<template>
    <b-container>
        <div v-for="p in this.pharmacies" :key="p.id">
            
            <b-container class="pt-2 pb-2">
            <b-row class="w-100 hover-shadow pt-2 pb-2 border">
                    <b-col> 
                        <!-- <router-link :to="{name: 'PharmacyPreview', params: {id: p.id}}"> -->
                            <img class="pharmacy_img" src="../pharmacyStock.jpg" alt="" @click="seeMore(p)">
                        
                    </b-col>
                    <b-col lg="6" md="4" sm="4">
                        <b-row>
                            <h1 class="text-left"> {{p.name}} </h1>
                        </b-row>
                        <b-row class="h-50 mb-4" style="max-height:110px">
                            <p class="text-left text">{{p.description}} </p>
                            
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

//const apiURL = "http://localhost:8080/api/pharmacy";

export default {
    name: 'PharmacyCard',
    components: {
        
    },
    props: {
        pharmacies: Array,
    },

  methods: {
      addressToString: function(address) {
        return address.street + " " + address.number + ", " + address.city + " " + address.country;
      },

      seeMore(p) {
          console.log(this.$router.currentRoute.path);
          this.$router.push("PharmacyPreview/" + p.id);
          console.log(this.$router.currentRoute.path);
        
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