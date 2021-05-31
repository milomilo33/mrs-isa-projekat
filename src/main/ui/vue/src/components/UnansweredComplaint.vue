<template>
  <div class="card">
    <b-alert v-model="showDismissibleAlert" dismissible fade variant="danger">
      Could not send complaint.
    </b-alert>
    <b-alert v-model="showSuccessAlert" dismissible fade variant="success">
      Your complaint has been sent.
    </b-alert>
      <h5 class="card-text">{{complaint.description}}</h5>
      <p class="card-text" id="colorIt">{{objectOfComplaint}}</p>
       <div v-show="seen" id="hide">
      <hr/>
      <label>Your answer:</label>
      <input v-model="response" class="form-control">
       <button type="button" class="btn btn-secondary" @click="sendResponse">
              Send
            </button>
      </div>
  </div>
</template>

<script>
import { defineComponent } from '@vue/composition-api'

export default defineComponent({
  name: "UnnasweredComplaint",
  props: {
    complaint: Object
  },
  data(){
    return{
      user: "",
      objectOfComplaint: "",
      pharmacy: null,
      employee: null,
      mockupComplaint: null,
      response: "",
      showSuccessAlert: false,
      showDismissibleAlert: false,
      seen: true,
      role: "",
    }
  },
  mounted(){
    
    this.user = JSON.parse(
      atob(localStorage.getItem("token").split(".")[1])
    ).sub;

    this.role = JSON.parse(
      atob(localStorage.getItem("token").split(".")[1])
    ).role;

    if(this.role=="ROLE_PATIENT"){
      this.seen = false;
    }

    if(this.complaint.employee == null){
      this.axios
        .get(`/api/pharmacy/` + parseInt(this.complaint.pharmacy), {
          headers: { Authorization: "Bearer " + localStorage.getItem("token") },
        })
        .then((response) => {
          this.pharmacy = response.data;
          this.objectOfComplaint = this.pharmacy.name;
        })
        .catch((error) => console.log(error.response.data));

      
      
    }
    else{
       this.axios
        .get(`/api/pharmacist/` + this.complaint.employee, {
          headers: { Authorization: "Bearer " + localStorage.getItem("token") },
        })
        .then((response) => {
          this.employee = response.data;
          this.objectOfComplaint = this.employee.name + " " + this.employee.lastName;
        })
        .catch((error) => console.log(error.response.data));

        if(this.employee == null){
          this.axios
            .get(`/api/dermatologist/` + this.complaint.employee, {
              headers: { Authorization: "Bearer " + localStorage.getItem("token") },
            })
            .then((response) => {
              this.employee = response.data;
              this.objectOfComplaint = this.employee.name + " " + this.employee.lastName;
            })
            .catch((error) => console.log(error.response.data));
        }

      
  }
},
methods: {
  sendResponse(){
    if(this.response==""){
      this.showDismissibleAlert = true;
    }
    else{
     this.axios.post(
          `/api/complaints/update/`,
          {
            id: this.complaint.id,
            employee: this.complaint.employee,
            pharmacy: this.complaint.pharmacy,
            description: this.complaint.description,
            response: this.response,
            responder: this.user,
            patient: this.complaint.patient
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
          }
        })
        .catch((error) => {
          console.log(error);
          this.showDismissibleAlert = true;
          this.showSuccessAlert= false;
        });
      }
    }
  }
})
</script>
<style scoped>
.form-control{
  border-color: rgb(83, 81, 81);
  margin-bottom: 1px;
}
#colorIt{
  color: green;
}
</style>
