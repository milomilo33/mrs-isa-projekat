<template>
  <div class="body">
    <b-card no-body class="overflow-hidden" style="max-width: 460px" >
      <b-row no-gutters>
        <b-col md="6">
          <img class="pharmacy_img" src="../medicament.jpg" alt="" />
        </b-col>
        <b-col md="6">
          <b-card-body :title="this.medicament.name">
            <b-card-text class="colorIt1">
              Type: {{ this.medicament.type }}
            </b-card-text>
            <b-card-text class="colorIt1">
              Rating: {{ this.ratings }}
            </b-card-text>
            <div>
              <b-button v-b-modal="'id' + medicament.id"
                >Specification</b-button
              >

              <b-modal
                content-class="my-class"
                :id="'id' + this.medicament.id"
                centered
                :title="this.medicament.name"
                header-bg-variant="dark"
                header-text-variant="light"
                body-bg-variant="light"
                body-text-variant="dark"
                :hide-footer="true"
              >
                <b-container fluid>
                  <b-row class="mb-1 text-center colorIt">
                    <p>
                      <b class="colorHeaders">Name :</b> &nbsp; #{{this.medicament.id}}
                      {{ this.medicament.name }} 
                    </p>
                  </b-row>
                  <b-row class="mb-1 colorIt">
                    <p>
                      <b class="colorHeaders"> Manufacturer: </b>&nbsp;
                      {{ this.medicament.manufacturer }}
                    </p>
                  </b-row>

                  <b-row class="mb-1 colorIt">
                    <p>
                      <b class="colorHeaders">Sructure:</b>
                      {{ this.medicament.structure }}
                    </p>
                  </b-row>
                  <b-row class="mb-1 colorIt">
                    <p>
                      <b class="colorHeaders">Annotations:</b>
                      {{ this.medicament.annotation }}
                    </p>
                  </b-row>
                  
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
                  <b-button class="m-2" @click="reserveMedicament()"> Reserve medicament </b-button>
                  
                </b-row>
                <b-row>
                  <datepicker class="m-2" v-model="date" @selected="date = $event.target.value" :disabled-dates="disabledDates"></datepicker>
                  <div class="m-2"> Date </div>
                  
                </b-row>
                <b-row v-html="unallowedQuantityMessage"></b-row>
              </b-modal>
            </div>
            
          </b-card-body>
        </b-col>
      </b-row>
    </b-card>
  </div>
</template>


<script>
import Datepicker from 'vuejs-datepicker';


export default {
  name: "MedicamentPreview",
  props: {
    medicament: Object,
  },
  components: {
    Datepicker
  },

  data() {
    return {
      ratings: 0,
      modal: "",
      show: false,
      nameM: "",
      amount: null,
      date: "",
      success: false,
      unallowedQuantityMessage: "",
      isReserved: false,
      disabledDates: {
        to: new Date(Date.now() - 8640000)
      }
    };
  },
  
  mounted() {
    this.nameM = this.medicament.name;
    this.axios
      .get(
        `/api/medicaments/ratings/` +
          parseInt(this.medicament.id),{
          headers: {Authorization: "Bearer " + localStorage.getItem('token')}
          }
      )
      .then((response) => {
        this.ratings = response.data;
        //console.log(this.ratings);
      })
      .catch((error) => console.log(error));
  },
  
  methods: {
    calculateRating: function (ratings) {
      var total = 0;
      for (var i = 0; i < ratings.lenght; i++) {
        total += ratings[i];
      }

      return total / ratings.lenght;
    },

    reserveMedicament() {
      //console.log(this.amount);
      if(this.amount !== null && this.date !== null) {
        this.axios.post(`/api/patients/reserve/`, {
          patientEmail: JSON.parse(atob(localStorage.getItem('token').split(".")[1])).sub,
          medicament: this.medicament,
          expiryDate: this.date,
          quantity: this.amount
        },{headers: {
            Authorization: "Bearer " + localStorage.getItem('token')
            }
        })
        .then(this.isReserved = true)
        .catch(error => {
          console.log(error);
          this.unallowedQuantityMessage = `<p class="ml-2" style="color:red;">Reservation quantity too big.</p>`;
        });
        //console.log(this.medicament.id);
        //console.log(this.date)
        if(this.isReserved) { 
          alert("Rezervacija uspešno izvršena!");
          this.isReserved = false;
        }
      }
    },
  },
};
</script>
<style scoped>
.colorHeaders {
  color: #009933;
}

.pharmacy_img {
  max-height: 30rem;
  max-width: 30rem;
}
.colorIt1{
  background-color: rgb(255, 255, 204,0.7);
  border-radius: 5%;
}

</style>