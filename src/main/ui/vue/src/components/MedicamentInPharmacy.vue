<template>
  <div class="cotainer">
    
    <div>
       
      <div class="col-md-12 row justify-content-center">
        <div class="card">
            <div class="card-header">Medicament Specification</div>
            
         
<b-alert v-model="showSuccessAlert" dismissible fade variant="success">
            Success! You gave this medicament {{rating}} stars.
    </b-alert>
          <div class="card-body">
            <b-row class="mb-4">
              <b-col>
                <h3 class="med-specification" style="text-align: left;">{{this.medicament.name}}</h3>
              </b-col>
              <b-col class="rate" v-if="role === 'ROLE_PATIENT'">
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
            </b-row>
            
            <p style="text-align: left;"><b class="colorHeaders"> Manufacturer: </b>&nbsp; {{this.medicament.manufacturer}}</p>
            <p style="text-align: left;"><b class="colorHeaders">Medicement Form: </b>{{this.medicament.form}}</p>
            <p style="text-align: left;"><b class="colorHeaders">Issuance Mode: </b>{{this.medicament.mode}}</p>
            <p style="text-align: left;"><b class="colorHeaders">Structure: </b> {{this.medicament.structure}}</p>
            <p style="text-align: left;"><b class="colorHeaders">Annotations: </b> {{this.medicament.annotation}}</p>
            
          </div>
        </div>
        <div class="card">
          <div class="card-header">Available in Pharmacies: </div>
          <div class="row row-cols-1 row-cols-md-3 g-4">
            <div class="col" v-for="item in pricelist" v-bind:key="item.id">
              <div class="card h-100">
                <img
                src="../pharmacyStock.jpg"
                class="card-img-top"
                alt="..."
                />
              <div class="card-body">
              <h5 class="card-title">{{item.pharmacy.name}}</h5>
              <p class="card-text">
                {{item.pharmacy.description}}
                {{item.price[0].value}}
              </p>
              <b-button v-b-modal="'id' + item.id"
                >Reserve</b-button
              >
              

              <b-modal
                content-class="my-class"
                :id="'id' + item.id"
                centered
                header-bg-variant="dark"
                header-text-variant="light"
                body-bg-variant="light"
                body-text-variant="dark"
                :hide-footer="true"
              >
                <b-container fluid>
              
                </b-container>
                <template #modal-header="{ close }">
                    <b-button
                      size="sm"
                      @click="close()"
                    >
                      Close
                    </b-button>
                  </template>
                <b-row>
                  <input type="number" placeholder="Quantity" class="m-2" :value="amount" @input="amount = $event.target.value">
                  <datepicker class="m-2" placeholder="Date" v-model="date" @selected="date = $event.target.value"></datepicker>
                 
                </b-row>
                <b-row>
                   <b-button variant="success" class="m-2" @click="reserveMedicament()"> Reserve medicament </b-button>
                  
                  
                </b-row>
              </b-modal>
            </div>
          </div>
        </div>
  </div>
        </div>
      </div>  
    </div>
  </div>
</template>

<script>
import Datepicker from 'vuejs-datepicker';
import { defineComponent } from "@vue/composition-api";

export default defineComponent({
  name: "MedicamentInPharmacy",
  components: {
    Datepicker
  },
  data() {
    return {
      medicament: "",
      pricelist: "",
      amount: "",
      date: null,
      rating: null,
      role: '',
      showSuccessAlert: false,

    }
  },

  computed: {
    rating: function() {
      this.postRating;
    }
  },

  mounted(){
    this.role = JSON.parse(
        atob(localStorage.getItem("token").split(".")[1])
    ).role;

     this.axios
      .get(
        `/api/medicaments/` +
          parseInt(this.$route.params.id),{
          headers: {Authorization: "Bearer " + localStorage.getItem('token')}
          }
      )
      .then((response) => {
        this.medicament = response.data;
        if(this.role === 'ROLE_PATIENT') {
          this.getRatingOfUser();
        }
      })
      .catch((error) => console.log(error));

    this.axios
      .get(
        `/api/pricelistItems/` +
          parseInt(this.$route.params.id),{
          headers: {Authorization: "Bearer " + localStorage.getItem('token')}
          }
      )
      .then((response) => {
        this.pricelist = response.data;
        console.log(this.pricelist);
      })
      .catch((error) => console.log(error));

      
  },
  methods: {

    getRatingOfUser() {
      var patientEmail = JSON.parse(atob(localStorage.getItem('token').split(".")[1])).sub;
      this.axios.get(`http://localhost:8080/api/patients/get_rating/${patientEmail}/${this.medicament.id}/medicament`)
        .then(response => 
        this.rating = response.data
        )
        .catch(error => console.log(error));
    },

    postRating(rating) {
      this.rating = rating
      console.log(this.rating);
      this.axios.post('http://localhost:8080/api/patients/rating', {
        rateType: 1,
        ratedEntityId: this.medicament.id,
        rating: this.rating,
        patientEmail: JSON.parse(atob(localStorage.getItem('token').split(".")[1])).sub,
      },
      {
        headers: {
          Authorization: "Bearer " + localStorage.getItem('token'),
      }}).then(() => this.showSuccess());
    },

    reserveMedicament() {
      //console.log(this.amount);
      console.log(this.date);
      if(this.amount !== null && this.date !== null) {
        this.axios.post(`/api/patients/reserve/`, {
          patientEmail: "anasimic@gmail.com",
          medicament: this.medicament,
          expiryDate: this.date,
          quantity: this.amount
        },{headers: {
            Authorization: "Bearer " + localStorage.getItem('token')
            }
        });
        //console.log(this.medicament.id);
        //console.log(this.date)
        alert("Rezervacija izvršena!");
      }
    },

    showSuccess() {
      this.showSuccessAlert = true;
      
    }
  }
})
</script>
<style scoped>
  .colorHeaders {
    color: #009933;
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
