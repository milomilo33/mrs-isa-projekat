<template>

<div id="PharmacyPreview">
  <PharmacyBasicInfo />
  <hr>

  <b-container> 
    <b-row align-h="start" class="pl-2 pb-1">
      <b-button size="lg" variant="light" href="#">
        All medicaments
      </b-button>
    </b-row>

    <b-row  class="med" v-for="p in this.medicaments" :key="p.id">
      <MedicamentPreview :medicament="p"> </MedicamentPreview>
    </b-row>
  </b-container>
 
  <hr>
 
  <b-container>
    <b-row align-h="start" class="pl-2 pb-1">
      <b-button size="lg" variant="light" href="#">
        All Employees
      </b-button>
    </b-row>
    <b-row  class="empl" v-for="p in this.employees" :key="p.id">
      <EmployeeCard :employee="p"> </EmployeeCard>
    </b-row> 
  </b-container>

  <br>

</div>

</template>

<script>
import { defineComponent } from '@vue/composition-api'
import PharmacyBasicInfo from '../components/PharmacyBasicInfo.vue'
import MedicamentCard from '../components/MedicamentCard.vue'
import EmployeeCard from '../components/EmployeeCard.vue'
import Footer from '../components/Footer.vue'
import MedicamentPreview from "../components/MedicamentPreview";
export default defineComponent({
  name: 'PharmacyPreview',
  components: {
    PharmacyBasicInfo,
    MedicamentCard,
    EmployeeCard,
    Footer,
    MedicamentPreview,

  },

  props: {
    id:Object,
  },
  
  data() {
    return {
      medicaments :[],
      employees :[],
      pharmacyId: "",
    };
  },

   mounted() {
     console.log("ID", this.id);
    var AdminUsername = JSON.parse(
      atob(localStorage.getItem("token").split(".")[1])
    ).sub;
     var userRole = JSON.parse(
        atob(localStorage.getItem("token").split(".")[1])
      ).role;
       var self = this;

      if(userRole == "ROLE_PHARMACY_ADMIN" && self.id == null){
          self.axios
          .get(`/api/pharmacyAdmin/` + AdminUsername, {
            headers: {
            Authorization: "Bearer " + localStorage.getItem('token'),
          },
      })
      .then(function (response) {
        self.pharmacyId = response.data.pharmacy.id;
        self.FinAll();
      })
      .catch(function (error) {
        console.log(error);
      });
   }else{
      console.log(self.id);
      self.pharmacyId = self.id;
      self.FinAll();
   }
   },
   methods:{
   FinAll(){
     
    var self = this;
    self.axios
      .get(`/api/pharmacy/medicamentItems/`+parseInt(self.pharmacyId),{
          headers: {Authorization: "Bearer " + localStorage.getItem('token')}
        })
      .then(function (response) {
        for (var i = 0; i < response.data.length; i++) {
          console.log(response);
          var item = {};
          item.id = response.data[i].medicament.id;
          item.name = response.data[i].medicament.name;
          item.type = response.data[i].medicament.type;
          item.manufacturer = response.data[i].medicament.manufacturer;
          item.structure = response.data[i].medicament.structure;
          item.annotation = response.data[i].medicament.annotation;
          self.medicaments.push(item);
        }
      });

      self.axios
      .get(`http://localhost:8080/api/pharmacy/pharmacists/`+parseInt(self.pharmacyId),{
          headers: {Authorization: "Bearer " + localStorage.getItem('token')}
        })

      .then(function (response) {
        for(var i = 0;i<response.data.length;i++){
           var count = 0;
            for(var j=0;j<response.data[i].workHours.length;j++){
                if(response.data[i].workHours[j].deleted){
                        count++;
                }
            }
        if(count != 7){
          var item = {};
          item.name = response.data[i].name;
          item.lastName = response.data[i].lastName;
          item.email = response.data[i].email;
          item.phoneNumber = response.data[i].phoneNumber;
          item.address = response.data[i].address.street + " "+response.data[i].address.number+", "+response.data[i].address.city;
          item.e = "Pharmacist";
          item.appointments = response.data[i].appointments;
          self.employees.push(item);
        }
        }
  });
  self.axios
      .get(`http://localhost:8080/api/pharmacy/dermatologists/`+parseInt(self.pharmacyId),{
          headers: {Authorization: "Bearer " + localStorage.getItem('token')}
        })
     
      .then(function (response) {
        for(var i = 0;i<response.data.length;i++){
            var count = 0;
            for(var j=0;j<response.data[i].workHours.length;j++){
                if(response.data[i].workHours[j].deleted){
                        count++;
                }
            }
        if(count != 7){
            
          var item = {};
          item.name = response.data[i].name;
          item.lastName = response.data[i].lastName;
          item.email = response.data[i].email;
          item.phoneNumber = response.data[i].phoneNumber;
          item.e = "Dermatologist";
          item.address = response.data[i].address.street + " " + response.data[i].address.number + ", " + response.data[i].address.city;
          item.appointments = response.data[i].appointments;
          self.employees.push(item);
        }
      }
      });
}}
})



</script>

<style scoped>
  .link {
    font-size: 2rem;
  }
  a {
    color: #0e9931;
  }
  .med {
    display :inline-block;
    margin-left: -40px;
    margin-right: 95px;
  }

   .empl {
    display :inline-block;
    margin-left: -40px;
    margin-right: 50px;
  }
  
</style>
