<template>
<div id="pharmacy-basic-info">
    <b-container>
       <b-alert v-model="showRatingAlert" dismissible fade variant="success">
            Success! You gave this pharmacy {{rating}} stars.
          </b-alert>
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
            
            <b-col class="rate" v-if="role === 'ROLE_PATIENT'" style="margin-right:auto;">
                <input type="radio" id="star5" name="rate" value="5" @click="postRating(5)" v-model.number="rating"/>
                <label for="star5" title="text"></label>
                <input type="radio" id="star4" name="rate" value="4" @click="postRating(4)" v-model.number="rating"/>
                <label for="star4" title="text"></label>
                <input type="radio" id="star3" name="rate" value="3" @click="postRating(3)" v-model.number="rating"/>
                <label for="star3" title="text"></label>
                <input type="radio" id="star2" name="rate" value="2" @click="postRating(2)" v-model.number="rating"/>
                <label for="star2" title="text"></label>
                <input type="radio" id="star1" name="rate" value="1" @click="postRating(1)" v-model.number="rating"/>
                <label for="star1" title="text"></label> 
            </b-col> 
            <b-col class="rate" v-if="role === 'ROLE_PHARMACY_ADMIN'" style="margin-right:auto;">
                <input type="radio" id="star5" name="rate" value="5" v-model.number="rating" disabled=true/>
                <label for="star5" title="text"></label>
                <input type="radio" id="star4" name="rate" value="4"  v-model.number="rating" disabled=true/>
                <label for="star4" title="text"></label>
                <input type="radio" id="star3" name="rate" value="3"  v-model.number="rating" disabled=true/>
                <label for="star3" title="text"></label>
                <input type="radio" id="star2" name="rate" value="2"  v-model.number="rating" disabled=true/>
                <label for="star2" title="text"></label>
                <input type="radio" id="star1" name="rate" value="1"  v-model.number="rating" disabled=true/>
                <label for="star1" title="text"></label> 
            </b-col> 
            <b-row>
              </b-row>
            </b-row>
            <b-row class="h-75" cols="8" align-h="start" style="min-height:150px; max-height:150px;">
              <p class="text-left">{{ pharmacy.description }}</p>
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
        showRatingAlert: false,
        role: '',
        rating: null,
        coordinates: [],
        idp:0,
      }
    },

    mounted() {
      var _this = this;
      let userRole = JSON.parse(
        atob(localStorage.getItem("token").split(".")[1])
      ).role;
      _this.role = userRole;
      _this.user = JSON.parse(
        atob(localStorage.getItem("token").split(".")[1])
      ).sub;
      var d = this.$route.params.id;
      if(userRole =="ROLE_PHARMACY_ADMIN" && d === undefined){
       
         _this.axios
          .get(process.env.VUE_APP_API_URL + `/pharmacyAdmin/` + _this.user, {
            headers: {
            Authorization: "Bearer " + localStorage.getItem('token'),
          },
      })
      .then(response=> {
          
        var self = this;
        self.idp = parseInt(response.data.pharmacy.id);
         _this.findPharmacy(self.idp);
         _this.findRating(self.idp);
       
      })
      .catch(error=> {
        console.log(error);
      });
      }else{
      this.axios.get(process.env.VUE_APP_API_URL + `/pharmacy/${this.$route.params.id}`, {
          headers: {Authorization: "Bearer " + localStorage.getItem('token')}
        })
      .then(response => {
        this.pharmacy = response.data;
        this.updateCoordinatesHandler();
        })
      .catch(error => console.log(error));
      }
    if(userRole == "ROLE_PATIENT")
    {
        this.axios.get( process.env.VUE_APP_API_URL + `/patients/subscribedPharmacies/${this.user},`+parseInt(this.$route.params.id), {
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

      this.getRatingOfUser();
    }
    else
    {
      
      _this.sub = false;
      _this.unsub = false;
    }
    
    
  },

  methods: {

    getRatingOfUser() {
      var patientEmail = JSON.parse(atob(localStorage.getItem('token').split(".")[1])).sub;
      this.axios.get(process.env.VUE_APP_API_URL + `/patients/get_rating/${patientEmail}/${this.$route.params.id}/pharmacy`)
        .then(response => 
        this.rating = response.data
        )
        .catch(error => console.log(error));
    }, 

    postRating(rating) {
      this.rating = rating
      console.log(this.rating);
      this.axios.post(process.env.VUE_APP_API_URL + '/patients/rating', {
        rateType: 0,
        ratedEntityId: this.$route.params.id,
        rating: this.rating,
        patientEmail: JSON.parse(atob(localStorage.getItem('token').split(".")[1])).sub,
      },
      {
        headers: {
          Authorization: "Bearer " + localStorage.getItem('token'),
      }}).then(() => this.showRatingAlert = true).catch(error => console.log(error.response.data));
    },
    // guessCoordinatesFromLocation: function() {
    //   console.log("APOTEKA", this.pharmacy);
		// 	const url =
		// 		"https://nominatim.openstreetmap.org/search/" +
		// 		this.pharmacy.address.city +
		// 		", " +
		// 		this.pharmacy.address.street +
		// 		" " +
		// 		this.pharmacy.address.number;
      
		// 	this.axios
		// 		.get(url, {
		// 			params: {
		// 				format: "json",
		// 				limit: 1,
		// 				"accept-language": "en",
		// 			},
		// 		})
		// 		.then((response) => {
		// 			if (response.data && response.data.lenght != 0) {
		// 				const { lon, lat } = response.data[0];
    //         console.log(lon, lat);
		// 				this.updateCoordinatesHandler([lon, lat]);
		// 			}
		// 		})
		// 		.catch(() => {
    //                 //console.log(error);
		// 			alert('Could not find coordinates based on given info.', '');
		// 		});
		// },

    updateCoordinatesHandler: function() {
			this.coordinates[0] = this.pharmacy.address.longitude;
			this.coordinates[1] = this.pharmacy.address.latitude;     
		},

    subscribe: function(){
      this.showRatingAlert = false;
      let _this = this;
      this.axios
        .post(process.env.VUE_APP_API_URL + `/patients/subscribe`, {
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
      this.showRatingAlert = false;
      let _this = this;
      this.axios
        .post(process.env.VUE_APP_API_URL + `/patients/unsubscribe`, {
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
    },
    findPharmacy(id){
      var _this= this;
         _this.axios.get(process.env.VUE_APP_API_URL + `/pharmacy/`+id, {
          headers: {Authorization: "Bearer " + localStorage.getItem('token')}
        })
      .then(response => {
        _this.pharmacy = response.data;
        _this.updateCoordinatesHandler();
        })
      .catch(error => console.log(error));
    },
    findRating(){
    var _this= this;
              _this.axios.get(process.env.VUE_APP_API_URL + `/pharmacy/rating/`+_this.idp, {
          headers: {Authorization: "Bearer " + localStorage.getItem('token')}
        })
      .then(response => {
        this.rating = response.data;
        })
      .catch(error => console.log(error));
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

  .card {
		width: 75%;
		
	}

	.rate {
	float: left;
	height: 46px;
	padding: 0 10px;
	}
	.rate:not(:checked) > input {
		position:absolute;
		top:-9999px;
	}
	.rate:not(:checked) > label {
		float:right;
		width:1em;
		overflow:hidden;
		white-space:nowrap;
		cursor:pointer;
		font-size:30px;
		color:#ccc;

	}
	.rate:not(:checked) > label:before {
		content: '★ ';
	}
	.rate > input:checked ~ label {
		color: #ffc700;    
	}
	.rate:not(:checked) > label:hover,
	.rate:not(:checked) > label:hover ~ label {
		color: #deb217;  
	}
	.rate > input:checked + label:hover,
	.rate > input:checked + label:hover ~ label,
	.rate > input:checked ~ label:hover,
	.rate > input:checked ~ label:hover ~ label,
	.rate > label:hover ~ input:checked ~ label {
		color: #c59b08;
	}
  
</style>