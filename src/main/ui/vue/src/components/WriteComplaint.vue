<template>
  <div>
     <h3>Write Complaint</h3>
    <hr />
    <b-alert v-model="showDismissibleAlert" dismissible fade variant="danger">
        Could not send complaint. {{message}}
      </b-alert>
      <b-alert v-model="showSuccessAlert" dismissible fade variant="success">
        Your complaint has been sent.
      </b-alert>
    <div class="form-group row">
      <label  class="col-md-4 col-form-label text-md-right"
        >Choose dermatologist</label
      >
      <label class="col-md-4 col-form-label text-md-right"
        >Choose pharmacist</label
      >
       <label class="col-md-4 col-form-label text-md-right"
        >Choose pharmacy</label
      >

      <div class="col-md-4">
        <b-form-select v-model="employee" @change="setNullPharmacy">
          <option v-for="d in dermatologist" :value="d" :key="d.email">
            {{ d.name }} 
          </option>
        </b-form-select>
      </div>

      
      <div class="col-md-4">
        <b-form-select v-model="employee" @change="setNullPharmacy">
          <option v-for="p in pharmacists" :value="p" :key="p.email">
            {{ p.name }} 
          </option>
        </b-form-select>
      </div>

     
      <div class="col-md-4">
        <b-form-select v-model="pharmacy" @change="setNullEmployee">
          <option v-for="p in pharmacies" :value="p" :key="p.id">
            {{ p.name }}
          </option>
        </b-form-select>
      </div>
    </div>
    <div class="form-group row">
      <label class="col-md-2 col-form-label text-md-right">Write complaint: </label>
      <div class="col-md-8">
                  <input
                   
                    class="form-control"
                    name="description"
                    v-model="description"
                  />
      </div>
    </div>
    <div class="form-group row">
      
      <div class="col-md-12">
        <button type="button" class="btn btn-secondary" @click="sendComplaint">
              Send
            </button>
      </div>
    </div>
  </div>
</template>
<script>
import { defineComponent } from "@vue/composition-api";

export default defineComponent({
  name: "WriteComplaint",
  data() {
    return {
      patient: "",
      dermatologist: [],
      pharmacists: [],
      pharmacies: [],
      employee: null,
      pharmacy: null,
      description: "",
      showDismissibleAlert: false,
      showSuccessAlert: false,
      message: "",
    }
  },
  mounted() {
    this.patient = JSON.parse(
      atob(localStorage.getItem("token").split(".")[1])
    ).sub;

    this.axios
      .get( process.env.VUE_APP_API_URL`/complaints/allPharmacist/` + this.patient, {
        headers: { Authorization: "Bearer " + localStorage.getItem("token") },
      })
      .then((response) => {
        this.pharmacists = response.data;
        console.log(this.pharmacists);
      })
      .catch((error) => console.log(error.response.data));

    this.axios
      .get(process.env.VUE_APP_API_URL +`/complaints/allDermatologists/` + this.patient, {
        headers: { Authorization: "Bearer " + localStorage.getItem("token") },
      })
      .then((response) => {
        this.dermatologist = response.data;
      })
      .catch((error) => console.log(error.response.data));

    this.axios
      .get( process.env.VUE_APP_API_URL +`/complaints/allPharmacies/` + this.patient, {
        headers: { Authorization: "Bearer " + localStorage.getItem("token") },
      })
      .then((response) => {
        this.pharmacies = response.data;
        console.log(this.pharmacies);
      })
      .catch((error) => console.log(error.response.data));
  },
  methods: {
      sendComplaint(){
        var sendPharmacy = this.pharmacy;
        var sendEmployee = this.employee;
        var error_found = false;

        if(this.employee != null){
          sendEmployee = this.employee.email;
        }
        if(this.pharmacy != null){
          sendPharmacy = this.pharmacy.id;
        }
        if(this.description == ""){
          error_found = true;
        }

        if(error_found == false){
            this.axios.post(process.env.VUE_APP_API_URL+
            `/complaints/save/`+this.patient,
            {
              employee: sendEmployee,
              pharmacy: sendPharmacy,
              description: this.description,
              patient: this.patient
            },
            {
              headers: {
                Authorization: "Bearer " + localStorage.getItem("token"),
              },
            }
          )
          .then((response) => {
            if (response.data != null) {
              this.showSuccessAlert = true;
              this.showDismissibleAlert = false;
              this.updateData();
            }
          })
          .catch((error) => {
            console.log(error);
            this.message = "";
            this.showDismissibleAlert = true;
            this.showSuccessAlert= false;
          });
        }
        else{
          this.message = " Complaint cannot be empty";
          this.showDismissibleAlert = true;
        }
       
      }
  ,
  setNullPharmacy(){
    this.pharmacy = null;
    console.log(this.pharmacy+ "  "+this.employee);
  },
  setNullEmployee(){
    this.employee = null;
    console.log(this.pharmacy+ " " + this.employee)
  },
  updateData(){
    this.axios
      .get( process.env.VUE_APP_API_URL +`/complaints/allPharmacist/` + this.patient, {
        headers: { Authorization: "Bearer " + localStorage.getItem("token") },
      })
      .then((response) => {
        this.pharmacists = response.data;
        console.log(this.pharmacists);
      })
      .catch((error) => console.log(error.response.data));

    this.axios
      .get(process.env.VUE_APP_API_URL+`/complaints/allDermatologists/` + this.patient, {
        headers: { Authorization: "Bearer " + localStorage.getItem("token") },
      })
      .then((response) => {
        this.dermatologist = response.data;
      })
      .catch((error) => console.log(error.response.data));

    this.axios
      .get(process.env.VUE_APP_API_URL +`/complaints/allPharmacies/` + this.patient, {
        headers: { Authorization: "Bearer " + localStorage.getItem("token") },
      })
      .then((response) => {
        this.pharmacies = response.data;
        console.log(this.pharmacies);
      })
      .catch((error) => console.log(error.response.data));
  }
},
});
</script>
