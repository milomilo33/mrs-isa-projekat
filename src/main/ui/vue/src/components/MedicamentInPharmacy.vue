<template>
  <div class="cotainer">
    <div class="row justify-content-center">
      <div class="col-md-8">
        <div class="card">
          <div class="card-header">Medicament Specification</div>
          <div class="card-body">
            <h3 class="med-specification">{{this.medicament.name}}</h3>
            <p><b class="colorHeaders"> Manufacturer: </b>&nbsp; {{this.medicament.manufacturer}}</p>
            <p><b class="colorHeaders">Medicement Form:</b>{{this.medicament.form}}</p>
            <p><b class="colorHeaders">Issuance Mode:</b>{{this.medicament.mode}}</p>
            <p><b class="colorHeaders">Structure:</b> {{this.medicament.structure}}</p>
            <p><b class="colorHeaders">Annotations:</b> {{this.medicament.annotation}}</p>
            
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

      pharmacy: {},
      therapyLength: ""
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
</style>
