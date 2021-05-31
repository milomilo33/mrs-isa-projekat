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
      <hr/>
      <p>Answer: {{complaint.response}}</p>
  </div>
</template>

<script>
import { defineComponent } from '@vue/composition-api'

export default defineComponent({
  name: "AnsweredComplaint",
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
    }
  },
  mounted(){
    
    this.user = JSON.parse(
      atob(localStorage.getItem("token").split(".")[1])
    ).sub;
    
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
