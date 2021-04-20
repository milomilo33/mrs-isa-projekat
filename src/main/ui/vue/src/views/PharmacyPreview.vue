<template>

<div id="PharmacyPreview">
  <b-navbar fixed="top" toggleable="lg" type="light" variant="light">
    <b-navbar-brand href="#">Home Page</b-navbar-brand>
    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
    <b-collapse id="nav-collapse" is-nav>
      <b-navbar-nav>
      </b-navbar-nav>
      
      <!-- Right aligned nav items -->
      <b-navbar-nav class="ml-auto">
        <Search />

        <b-nav-item-dropdown right>
          <!-- Using 'button-content' slot -->
          <template #button-content>
            <em class="pl-2">User</em>
          </template>
          <b-dropdown-item href="#">Sign In</b-dropdown-item>
        </b-nav-item-dropdown>
      </b-navbar-nav>
    </b-collapse>
  </b-navbar>

  <br>
  <b-container> 
    <b-row> 
      <b-col  cols ="1" md="1"><a href="#"> Appointments</a></b-col>
      <b-col  cols ="24" md="2"><a href="#"> Reservations</a></b-col>
      <b-col  cols ="1" md="2"><a href="#"> Promotions</a></b-col></b-row>
  </b-container>
  <br>
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
    MedicamentPreview

  },

  props: {
    
  },
  
  data() {
    return {
      medicaments :[],
      employees :[],
    };
  },

   mounted() {
    var self = this;
    self.axios
      .get(`http://localhost:8080/api/pharmacy/medicamentItems/${this.$route.params.id}`)
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
      .get(`http://localhost:8080/api/pharmacy/pharmacists/${this.$route.params.id}`)
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
          self.employees.push(item);
        }
        }
  });
  self.axios
      .get(`http://localhost:8080/api/pharmacy/dermatologists/${this.$route.params.id}`)
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
          item.appointments = response.data[i].appointments
          self.employees.push(item);
        }
      }
      });
}
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
