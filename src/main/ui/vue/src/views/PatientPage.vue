<template>
  <div>
    <div class="navbar-div" style="margin-bottom: 100px;">
    <b-navbar fixed="top" toggleable="lg" type="light" variant="light">
        <b-navbar-brand href="/PatientPage/PharmacyList">Home Page</b-navbar-brand>
        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
        <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <b-nav-item href="/PatientPage/MedicamentList">Medicaments</b-nav-item>
          <b-nav-item href="/PatientPage/PharmacyList"> Pharmacies</b-nav-item>
          
          <b-nav-item-dropdown left>
                <template #button-content>
                    Appointments
                </template>
                <b-dropdown-item href="/PatientPage/ReservedAppointmentsTable"> Reserved Appointments</b-dropdown-item>
                <b-dropdown-item href="/PatientPage/PastAppointments">Appointment history</b-dropdown-item>
                
            </b-nav-item-dropdown>
          <b-nav-item href="/PatientPage/ReservedMedicamentsTable"> Reserved Medicaments</b-nav-item>
          <b-nav-item href="/PatientPage/ePrescriptions"> ePrescriptions </b-nav-item>
        </b-navbar-nav>
        
        <!-- Right aligned nav items -->
        <b-navbar-nav class="ml-auto">
            <Search :path="'/PatientPage'"/>

            <b-nav-item href="#" style="margin-left: 10px;">
                <b-icon-calendar-2-week></b-icon-calendar-2-week>
            </b-nav-item>
            <b-nav-item-dropdown right>
                <template #button-content>
                    <em class="pl-2">User</em>
                </template>
                <b-dropdown-item href="#">Profile</b-dropdown-item>
                 <b-dropdown-item href="/PatientPage/ChangePassword">Change password</b-dropdown-item>
                <b-dropdown-item href="/Logout">Sign Out</b-dropdown-item>
            </b-nav-item-dropdown>
        </b-navbar-nav>
        </b-collapse>
    </b-navbar>
    </div>
    <router-view></router-view>
  </div>
</template>

<script>
import Search from '../components/Search'

export default {
  components: {
    Search
  },
  props: {
    // ...
  }
  // ...
}
</script>
