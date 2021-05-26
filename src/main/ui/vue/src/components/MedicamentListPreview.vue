<template>
  <div id="MedicamentsListPreview" class="div">
    <div v-if="substituteMode" style="text-align:center; margin-bottom: 3rem !important;">
      <h2>Substitute medicines for {{ substitutedMedicament.name }}</h2>
      <b-button variant="primary" @click="reloadAllMedicines">Show all medicines</b-button>
    </div>
    <section id="sidebar">
      <p class="colorIt">Search medicaments by its name</p>
      <input
        size="sm"
        class="mr-sm-2 search"
        placeholder="Search"
        v-model="search"
      />
      <b-button size="sm" class="my-2 my-sm-0 button" type="submit" @click = "findSearch"
        >Search</b-button
      >
      <hr />
      <p class="colorIt" v-if="!prescriptionMode"><b>Filters:</b></p>
      <div class="issuanceModeFilter" v-if="!prescriptionMode">
        <p><b>Choose issuance mode:</b></p>
        <div class="form-check">
          <input
            class="form-check-input"
            type="radio"
            name="issuanceMode"
            id="issuanceMode"
            value=1
            v-model="issuanceMode"
          />
          <label class="form-check-label" for="issuanceMode">
            With prescription
          </label>
        </div>

        <div class="form-check">
          <input
            class="form-check-input"
            type="radio"
            name="issuanceMode"
            id="issuanceMode1"
            value=0
            checked
            v-model="issuanceMode"
          />
          <label class="form-check-label" for="issuanceMode1">
            Without prescription
          </label>
        </div>
      </div>
      <div class="medicamentFormFilter" v-if="!prescriptionMode">
        <p><b>Choose medicament form:</b></p>
        <div class="form-check">
          <input
            class="form-check-input"
            type="radio"
            name="medicamentForm"
            id="medicamentForm"
            value=1
            v-model="medicamentForm"
          />
          <label class="form-check-label" for="medicamentForm">
            Capsule
          </label>
        </div>

        <div class="form-check">
          <input
            class="form-check-input"
            type="radio"
            name="medicamentForm"
            id="medicamentForm1"
            value=2
            checked
            v-model="medicamentForm"
          />
          <label class="form-check-label" for="medicamentForm1">
            Tablet
          </label>
        </div>
        <div class="form-check">
          <input
            class="form-check-input"
            type="radio"
            name="medicamentForm"
            id="medicamentForm2"
            value=0
            v-model="medicamentForm"
          />
          <label class="form-check-label" for="medicamentForm2">
            Powder
          </label>
        </div>
        <div class="form-check">
          <input
            class="form-check-input"
            type="radio"
            name="medicamentForm"
            id="medicamentForm3"
            value=5
            checked
            v-model="medicamentForm"
          />
          <label class="form-check-label" for="medicamentForm3">
            Gel
          </label>
        </div>
        <div class="form-check">
          <input
            class="form-check-input"
            type="radio"
            name="medicamentForm"
            id="medicamentForm4"
            value=3
            v-model="medicamentForm"
          />
          <label class="form-check-label" for="medicamentForm4">
            Ointments
          </label>
        </div>

        <div class="form-check">
          <input
            class="form-check-input"
            type="radio"
            name="medicamentForm"
            id="medicamentForm5"
            value=4
            checked
            v-model="medicamentForm"
          />
          <label class="form-check-label" for="medicamentForm5">
            Paste
          </label>
        </div>
          <div class="form-check">
          <input
            class="form-check-input"
            type="radio"
            name="medicamentForm"
            id="medicamentForm6"
            value=6
            v-model="medicamentForm"
          />
          <label class="form-check-label" for="medicamentForm6">
            Liquid
          </label>
        </div>

        <div class="form-check">
          <input
            class="form-check-input"
            type="radio"
            name="medicamentForm"
            id="medicamentForm7"
            value=7
            checked
            v-model="medicamentForm"
          />
          <label class="form-check-label" for="medicamentForm7">
            Syrup
          </label>
        </div>
        <b-button size="sm" class="my-2 my-sm-0 button" type="submit" @click = "findFilter"
        >Filter</b-button
      >
      </div>
    </section>
    <section id="products">
      <div class="row row-cols-2 row-cols-md-3 g-2">
        <div
          class="col-lg-4 col-md-6 col-sm-10 offset-md-0 offset-sm-1"
          v-for="p in this.medicaments"
          :key="p.id"
        >
          <div>
            <MedicamentPreview :type="type" :medicament="p" :allergies="allergies" :prescriptionMode="prescriptionMode" @seeMoreClicked="onSeeMore"> </MedicamentPreview>
          </div>
        </div>
      </div>
    </section>

    <div v-if="Object.keys(chosenMedicament).length !== 0" :key="chosenMedicament">
      <MedicamentInPharmacy :chosenMedicament="chosenMedicament" :appointment="appointment" :prescriptionMode=true @prescribed="onPrescribed" @substituted="onSubstituted"></MedicamentInPharmacy>
    </div>
  </div>
</template>

<script>
import { defineComponent } from "@vue/composition-api";
import MedicamentPreview from "../components/MedicamentPreview";
import MedicamentInPharmacy from "../components/MedicamentInPharmacy";

export default defineComponent({
  name: "MedicamentListPreview",
  components: {
    MedicamentPreview,
    MedicamentInPharmacy
  },

  data() {
    return {
      medicaments: [],
      search: "",
      issuanceMode: -1,
      medicamentForm: -1,
      allergies: [],
      type: '',
      medicamentsSearchCopy: [],
      medicamentsOriginal: [],
      chosenMedicament: {},
      substituteMode: false,
      substitutedMedicament: {}
    };
  },

  props: {
    prescriptionMode: {
      type: Boolean,
      default: false
    },
    appointment: Object
  },

  mounted() {
    this.type = JSON.parse(
        atob(localStorage.getItem("token").split(".")[1])
      ).role;
    
    if (!this.prescriptionMode)
      this.loadAllergies();
    else {
      this.loadNonAllergicMedsForPrescription();
      return;
    }

    if(this.type != "ROLE_PATIENT") {
      this.loadMeds();
    }
    
    
    
    
  },
  methods: {

    loadMeds() {
      this.axios
      .get(`/api/medicaments/all`, {
        headers: { Authorization: "Bearer " + localStorage.getItem("token") },
      })
      .then((response) => {
        
        this.medicaments = response.data;
        
        //console.log(this.medicaments);
      })
      .catch((error) => console.log(error.response.data));
    },

    loadNonAllergicMedsForPrescription() {
      let patientEmail = this.appointment.patient.email;
      let appointmentId = this.appointment.id;
      this.axios.get(`/api/medicaments/non-allergic-in-pharmacy?patientEmail=${patientEmail}&appointmentId=${appointmentId}`, {
        headers: { Authorization: "Bearer " + localStorage.getItem("token") },
      })
      .then((response) => {
        this.medicaments = response.data;
        this.medicamentsOriginal = this.medicaments;
        this.medicamentsSearchCopy = this.medicaments;
        //console.log(response.data);
      })
      .catch((error) => console.log(error.response.data));
    },

    loadAllergies() {
      this.axios.get(`/api/patients/get_allergies/` + JSON.parse(atob(localStorage.getItem('token').split(".")[1])).sub, {
        headers: { Authorization: "Bearer " + localStorage.getItem("token") },
      })
        .then(response => {
          console.log(response.data);
          this.allergies = response.data;
          this.loadMeds();
        })
        .catch(error => console.log(error.response.data)); 
    },
    findSearch(){
      if (this.prescriptionMode) {
        this.frontEndSearch();
        return;
      }
        
      this.axios
      .get(`/api/medicaments/search/`+this.search, {
        headers: { Authorization: "Bearer " + localStorage.getItem("token") },
      })
      .then((response) => {
        this.medicaments = response.data;
        //console.log(this.medicaments);
      })
      .catch((error) => console.log(error));
    },
    findFilter(){
      // if (this.prescriptionMode) {
      //   this.frontEndFilter();
      //   return;
      // }

      this.axios
      .get(`/api/medicaments/filter/mode=`+parseInt(this.issuanceMode)+`&form=`+parseInt(this.medicamentForm), {
        headers: { Authorization: "Bearer " + localStorage.getItem("token") },
      })
      .then((response) => {
        this.medicaments = response.data;
        //console.log(this.medicaments);
      })
      .catch((error) => console.log(error));
    },

    frontEndSearch() {
      this.medicaments = this.medicamentsSearchCopy.filter((value) => value.name.toLowerCase().includes(this.search.toLowerCase()));
      this.chosenMedicament = {};
    },

    onSeeMore(med) {
      this.chosenMedicament = med;
    },

    onPrescribed() {
      this.$emit('prescribed');
    },

    reloadAllMedicines() {
      this.medicaments = this.medicamentsOriginal;
      this.medicamentsSearchCopy = this.medicamentsOriginal;
      this.substituteMode = false;
      this.substitutedMedicament = {};
      this.chosenMedicament = {};
    },

    onSubstituted(payload) {
      this.substituteMode = true;
      this.substitutedMedicament = payload.substitutedMed;
      let quantity = payload.quantity;
      let pharmacy = payload.pharmacy;
      this.chosenMedicament = {};

      this.axios.get(`/api/medicaments/${this.substitutedMedicament.id}/substitutes?` +
                      `patientEmail=${this.appointment.patient.email}&quantity=${quantity}&pharmacyId=${pharmacy.id}`, {
                      headers: { Authorization: "Bearer " + localStorage.getItem("token") }
                    })
                    .then(response => {
                      this.medicaments = response.data;
                      this.medicamentsSearchCopy = this.medicaments;
                    })
                    .catch(error => {
                      console.log(error);
                    });
    }

  }
});
</script>

<style scoped>
.search {
  border-color: #6c757d;
  border-radius: 5%;
}
#sidebar {
  color: #6c757d;
  width: 25%;
  float: left;
}
#products {
  width: 100%;
  padding-left: 30px;
  margin-bottom: 100px;
}
.colorIt {
  color: #009933;
}
</style>