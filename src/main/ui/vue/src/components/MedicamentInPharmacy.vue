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
      
    }
  },
  mounted(){
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
  }
})
</script>
<style scoped>
.colorHeaders {
  color: #009933;
}
</style>
