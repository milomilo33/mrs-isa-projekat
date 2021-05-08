<template>
<div id="pharmacy-basic-info">
    <b-container>
       <b-alert v-model="showSuccessAlert" dismissible fade variant="success">
            Success! You subscribed to {{pharmacy.name}}.
          </b-alert>
          <b-alert v-model="showFailedAlert" dismissible fade variant="danger">
            Failed to subscribe.
          </b-alert>
          <b-alert v-model="showSuccessAlertUnsub" dismissible fade variant="success">
            Success! You unsubscribed to {{pharmacy.name}}.
          </b-alert>
          <b-alert v-model="showFailedAlertUnsub" dismissible fade variant="danger">
            Failed to unsubscribe.
          </b-alert>
    <b-row>
      <b-col cols="4" class="col-sm-12 col-md-4 col-lg-4">
        <img class="img" src="../pharmacyStock.jpg" alt="">
      </b-col>

      <b-col cols="8" align-v="start">
        <b-container>
            <b-row>
            <h1>{{ pharmacy.name }}</h1>
            <b-button variant="success" size="sm" class="buttons" v-if="sub" @click="subscribe">Subscribe</b-button>
            <b-button variant="outline-success" size="sm" class="buttons" v-if="unsub" @click="unsubscribe"> Unsubscribe</b-button>
            </b-row>
            <b-row class="h-75" cols="8" align-h="start" style="min-height:150px; max-height:150px;">
              <p class="text-left"> {{ pharmacy.description }}</p>
            </b-row>
            <b-row class="">
              <b-link v-b-modal.modal-map><h5>{{ this.addressToString(pharmacy.address) }}</h5></b-link>
                

              <b-modal id="modal-map" hide-footer size="xl">
                <template>
                  <MapContainer :coordinates="coordinates"/>
                </template>

                <b-button block class="mt-4" @click="$bvModal.hide('modal-map')">Close</b-button>
              </b-modal>
            </b-row>
    </b-container>
      </b-col>
    </b-row>
    </b-container>
</div>
</template>


<script>
import MapContainer from "./MapContainer.vue" 

export default {
    name: 'PharmacyBasicInfo',

    components: {
      MapContainer
    },

    props: {
      
    },

    data() {
      return {
        sub: '',
        unsub: '',
        pharmacy: '',
        user: null,
        showSuccessAlert: false,
        showFailedAlert: false,
        showSuccessAlertUnsub: false,
        showFailedAlertUnsub: false,
        coordinates: []
      }
    },

    mounted() {
      let _this = this;
      let userRole = JSON.parse(
        atob(localStorage.getItem("token").split(".")[1])
      ).role;
      this.user = JSON.parse(
        atob(localStorage.getItem("token").split(".")[1])
      ).sub;
      
      this.axios.get(`/api/pharmacy/${this.$route.params.id}`, {
          headers: {Authorization: "Bearer " + localStorage.getItem('token')}
        })
      .then(response => {
        this.pharmacy = response.data;
        this.guessCoordinatesFromLocation();
        })
      .catch(error => console.log(error));

    if(userRole == "ROLE_PATIENT")
    {
        this.axios.get(`/api/patients/subscribedPharmacies/${this.user},`+parseInt(this.$route.params.id), {
        headers: {Authorization: "Bearer " + localStorage.getItem('token')}
        })
      .then(response=> {
        if(response.data == "Found"){
          _this.sub = false;
          _this.unsub = true;
        }
        else
        {
          _this.sub = true;
          _this.unsub = false;
        }
      })
    }
    else
    {
      _this.sub = false;
      _this.unsub = false;
    }
    
    
  },

  methods: {

    guessCoordinatesFromLocation: function() {
      console.log("APOTEKA", this.pharmacy.address);
			const url =
				"https://nominatim.openstreetmap.org/search/" +
				this.pharmacy.address.city +
				", " +
				this.pharmacy.address.street +
				" " +
				this.pharmacy.address.number;
      
			this.axios
				.get(url, {
					params: {
						format: "json",
						limit: 1,
						"accept-language": "en",
					},
				})
				.then((response) => {
					if (response.data && response.data.lenght != 0) {
						const { lon, lat } = response.data[0];
            console.log(lon, lat);
						this.updateCoordinatesHandler([lon, lat]);
					}
				})
				.catch(() => {
                    //console.log(error);
					alert('Could not find coordinates based on given info.', '');
				});
		},

        updateCoordinatesHandler: function(coordinates) {
			this.coordinates[0] = coordinates[0];
			this.coordinates[1] = coordinates[1];
      
            
		},

    subscribe: function(){
      
      let _this = this;
      this.axios
        .post(`/api/patients/subscribe`, {
          pharmacy: this.pharmacy,
          user: this.user
        },{
          headers: {Authorization: "Bearer " + localStorage.getItem('token')}
        })
        .then(function () {
           _this.unsub = true;
           _this.sub = false;
          _this.showSuccessAlert = true;
        })
        .catch(function (error) {
          console.log(error);
          _this.showFailedAlert = true;
        });
    },
    unsubscribe: function(){
      let _this = this;
      this.axios
        .post(`/api/patients/unsubscribe`, {
          pharmacy: this.pharmacy,
          user: this.user
        },{
          headers: {Authorization: "Bearer " + localStorage.getItem('token')}
        })
        .then(function () {
            _this.unsub = false;
            _this.sub = true;
            _this.showSuccessAlertUnsub = true;
        })
        .catch(function (error) {
          console.log(error);
          _this.showFailedAlertUnsub = true;
        });
     
    },
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
  .buttons{
    margin-left: 4rem;
  }


  
</style>