<template>
  <div>
    <div class="navbar-div" style="margin-bottom: 100px;">
    <b-navbar fixed="top" toggleable="lg" type="light" variant="light">
        <b-navbar-brand href="/DermatologistPage/PharmacyList">Home Page</b-navbar-brand>
        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
        <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <b-nav-item href="/DermatologistPage/MedicamentList">Medicaments</b-nav-item>
          <b-nav-item href="/DermatologistPage/PharmacyList"> Pharmacies</b-nav-item>
            <b-nav-item-dropdown left>
                <template #button-content>
                    Examination
                </template>
                <b-dropdown-item href="/DermatologistPage/ExaminationSearch">Start an examination</b-dropdown-item>
                <b-dropdown-item href="#">Schedule an examination for a client</b-dropdown-item>
                <b-dropdown-item href="/DermatologistPage/ExaminedPatients?type=dermatologist">Examination history</b-dropdown-item>
                <b-dropdown-item href="/DermatologistPage/SearchPatients">Search patients</b-dropdown-item>
            </b-nav-item-dropdown>
            <b-nav-item href="/DermatologistPage/RequestDaysOff?type=dermatologist">Request days off</b-nav-item>
        </b-navbar-nav>
        
        <!-- Right aligned nav items -->
        <b-navbar-nav class="ml-auto">
            <Search :path="'/DermatologistPage'"/>

            <b-nav-item href="/DermatologistPage/WorkCalendar?type=dermatologist" style="margin-left: 10px;">
                <b-icon-calendar-2-week></b-icon-calendar-2-week>
            </b-nav-item>
            <b-nav-item-dropdown right>
                <template #button-content>
                    <em class="pl-2">User</em>
                </template>
                <b-dropdown-item href="/DermatologistPage/PatientProfile?type=dermatologist">Profile</b-dropdown-item>
                 <b-dropdown-item href="/DermatologistPage/ChangePassword">Change password</b-dropdown-item>
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
import { BIconCalendar2Week } from 'bootstrap-vue'
import Search from '../components/Search'

export default {
  components: {
    BIconCalendar2Week,
    Search
  },
  props: {
    // ...
  }
  // ...
}
</script>
