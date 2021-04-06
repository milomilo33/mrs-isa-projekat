<template>

<div id="PharmacyPreview">
  <b-navbar fixed="top" toggleable="lg" type="dark" variant="dark">
    <b-navbar-brand href="#">Home Page</b-navbar-brand>
    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
    <b-collapse id="nav-collapse" is-nav>
      <b-navbar-nav>
        <b-nav-item href="#">Medicaments</b-nav-item>
        <b-nav-item href="#">Appointments</b-nav-item>
        <b-nav-item href="#">Employees</b-nav-item>
         <b-nav-item href="#">Promotions</b-nav-item>
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

  <PharmacyBasicInfo />

  <hr>

  <!--<b-container> 
    <b-row align-h="start" class="pl-2 pb-1">
      <b-button size="lg" variant="light" href="#">
        All medicaments
      </b-button>
    </b-row>

    <b-row>
      <MedicamentCard />
      <MedicamentCard />
      <MedicamentCard />
    </b-row>

  </b-container>
 
  <hr>
 
  <b-container>
    <b-row align-h="start" class="pl-2 pb-1">
      <b-button size="lg" variant="light" href="#">
        All Employees
      </b-button>
    </b-row>
    <b-row>
        <EmployeeCard />
        <EmployeeCard />
        <EmployeeCard />
    </b-row>  
  </b-container>-->

  <br>

</div>

</template>

<script>
import { defineComponent } from '@vue/composition-api'
import PharmacyBasicInfo from '../components/PharmacyBasicInfo.vue'
import MedicamentCard from '../components/MedicamentCard.vue'
import EmployeeCard from '../components/EmployeeCard.vue'
import Footer from '../components/Footer.vue'
export default defineComponent({
  name: 'PharmacyPreview',
  components: {
    PharmacyBasicInfo,
    MedicamentCard,
    EmployeeCard,
    Footer
  },

  props: {
    
  },
  
  data() {
    return {
    }
  },
})



</script>

<style scoped>
  .link {
    font-size: 2rem;
  }
  
</style>
