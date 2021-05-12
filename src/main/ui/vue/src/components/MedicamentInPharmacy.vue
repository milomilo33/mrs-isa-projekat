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
        <div class="card" v-if="!prescriptionMode">
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
              <b-button v-if="role === 'ROLE_PATIENT'" v-b-modal="'id' + item.id"
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
                   <b-button variant="success" class="m-2" @click="reserveMedicament(item.pharmacy.id)"> Reserve medicament </b-button>
                  
                  
                </b-row>
              </b-modal>
            </div>
          </div>
        </div>
        </div>
        </div>

        <div class="card" v-if="prescriptionMode">
          <div class="card-header">In Your Pharmacy: </div>
          <div class="row row-cols-1 row-cols-md-3 g-4">
            <div class="col">
              <div class="card h-100">
                <img
                src="../pharmacyStock.jpg"
                class="card-img-top"
                alt="..."
                />
              <div class="card-body">
              <h5 class="card-title">{{this.pharmacy.name}}</h5>
              <p class="card-text">
                {{this.pharmacy.description}}
              </p>
              <b-button v-b-modal="'id0'">Prescribe</b-button>
              

              <b-modal
                content-class="my-class"
                :id="'id0'"
                ref="id0"
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
                  <input type="number" placeholder="Length of therapy" class="m-2" :value="therapyLength" @input="therapyLength = $event.target.value">
                </b-row>
                <b-row>
                   <b-button variant="success" class="m-2" @click="prescribeMedicament(pharmacy.id)"> Prescribe medicament </b-button>
                </b-row>
              </b-modal>
            </div>
          </div>
        </div>
        </div>
        </div>

      <b-modal ref="successful-prescription-modal" hide-footer title="Success" @hide="onCloseSuccessModal">
        <div class="d-block text-center">
            <p>Medicine successfully prescribed</p>
        </div>
        <b-button class="mt-3" variant="outline-success" block @click="onCloseSuccessModal">Close</b-button>
      </b-modal>

      <b-modal ref="substitute-prescription-modal" hide-footer title="Substitute medicine(s)" @hide="onCloseSubstituteModal">
        <div class="d-block text-center">
            <p>Not enough of this medicine in {{ pharmacy.name }}. Showing substitute medicines for {{ medicament.name }}.</p>
        </div>
        <b-button class="mt-3" variant="outline-warning" block @click="onCloseSubstituteModal">Close</b-button>
      </b-modal>

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
      role: '',
      rating: null,
      showSuccessAlert: false,

      pharmacy: {},
      therapyLength: ""
    }
  },
  computed: {
    rating: function() {
      this.postRating;
    }
  },
  props: {
    prescriptionMode: {
      type: Boolean,
      default: false
    },
    appointment: Object,
    chosenMedicament: Object
  },
  mounted(){
    this.role = JSON.parse(
        atob(localStorage.getItem("token").split(".")[1])
      ).role;

    if (this.prescriptionMode) {
      this.loadForPrescription();
      return;
    }
    
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

    

    reserveMedicament(pharmacy) {
      console.log(this.date);
      if(this.amount !== null && this.date !== null) {
        this.axios.post(`/api/patients/reserve/`, {
          patientEmail: "anasimic@gmail.com",
          medicament: this.medicament,
          expiryDate: this.date,
          quantity: this.amount,
          pharmacyId: pharmacy
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
    },
      
    prescribeMedicament(pharmacyId) {
      if (this.amount > 0) {
        this.axios.post(`/api/patients/prescribe/${this.appointment.medicalReportId}`, {
          patientEmail: this.appointment.patient.email,
          medicament: this.medicament,
          expiryDate: this.date,
          quantity: this.amount,
          pharmacyId
        },  {headers: {
              Authorization: "Bearer " + localStorage.getItem('token')
            }
        })
        .then(response => {
          console.log(response);
          this.showSuccessModal();
        })
        .catch(error => {
          if (error.response.status === 404) {
            this.showSubstituteModal();
          }
          else {
            console.log(error);
          }
        });
      }
    },

    loadForPrescription() {
      this.medicament = this.chosenMedicament;

      let appointmentId = this.appointment.id;
      this.axios.get(`/api/appointments/${appointmentId}/pharmacy`, {
                        headers: {
                            Authorization: "Bearer " + localStorage.getItem("token"),
                        },
                    })
                    .then(response => {
                      this.pharmacy = response.data;
                    })
                    .catch(error => {
                      console.log(error);
                    })
    },

    onCloseSuccessModal() {
      // this.$router.push({ name: 'DermatologistPageAppointmentPage',
      //                     params: { appointment: this.appointment,
      //                               medicalReportId: this.appointment.medicalReportId } })
      //             .catch(() => {});
      this.$refs['successful-prescription-modal'].hide();
      this.$refs['id0'].hide();
      this.$emit('prescribed');
    },

    onCloseSubstituteModal() {
      this.$refs['substitute-prescription-modal'].hide();
      this.$refs['id0'].hide();
      this.$emit('substituted', {
        substitutedMed: this.medicament,
        quantity: this.amount,
        pharmacy: this.pharmacy
      });
    },

    showSuccessModal() {
        this.$refs['successful-prescription-modal'].show();
    },

    showSubstituteModal() {
        this.$refs['substitute-prescription-modal'].show();
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
