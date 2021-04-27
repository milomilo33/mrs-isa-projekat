<template>
<div id="pharmacy-basic-info">
    <b-container>
    <b-row>
      <b-col cols="4" class="col-sm-12 col-md-4 col-lg-4">
        <img class="img" src="../pharmacyStock.jpg" alt="">
      </b-col>

      <b-col cols="8" align-v="start">
        <b-container>
            <b-row>
            <h1>{{ pharmacy.name }}</h1>
            </b-row>
            <b-row class="h-75" cols="8" align-h="start" style="min-height:150px; max-height:150px;">
              <p class="text-left"> {{ pharmacy.description }}</p>
            </b-row>
            <b-row class="">
              <h5>{{ this.addressToString(pharmacy.address) }}</h5>
            </b-row>
    </b-container>
      </b-col>
    </b-row>
    </b-container>
</div>
</template>


<script>

export default {
    name: 'PharmacyBasicInfo',
   
    props: {
      
    },

    data() {
      return {
        pharmacy: ''
      }
    },

    created() {
    this.axios.get(`/api/pharmacy/${this.$route.params.id}`, {
          headers: {Authorization: "Bearer " + localStorage.getItem('token')}
        })
      .then(response => {
        this.pharmacy = response.data;
        console.log(this.pharmacy);
        })
      .catch(error => console.log(error));
  },

  methods: {
    addressToString: function(address) {
      return address.street + " " + address.number 
        + ", " + address.city + " " + address.country;
    }
  }
}
</script>


<style scoped>
    .img {
    max-width: 100%;
    max-height: 100%;
  }

  .pharmacy {
    text-align: left;
    }

  .address {
    position: absolute;
    bottom: 0;
    left: 10;
  }
</style>