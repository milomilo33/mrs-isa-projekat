<template>
  <div>
    <div class="navbar-div" style="margin-bottom: 100px;">
    <b-navbar fixed="top" toggleable="lg" type="light" variant="light">
        <b-navbar-brand href="/PharmacistPage/PharmacyList">Home Page</b-navbar-brand>
        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
        <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <b-nav-item href="/PharmacistPage/MedicamentList">Medicaments</b-nav-item>
          <b-nav-item href="/PharmacistPage/PharmacyList"> Pharmacies</b-nav-item>
            <b-nav-item-dropdown left>
                <template #button-content>
                    Counseling
                </template>
                <b-dropdown-item href="#">Start a counseling session</b-dropdown-item>
                <b-dropdown-item href="#">Schedule a counseling session for a client</b-dropdown-item>
                <b-dropdown-item href="#">Counseling history</b-dropdown-item>
                <b-dropdown-item href="/PharmacistPage/SearchPatients">Search patients</b-dropdown-item>
            </b-nav-item-dropdown>
            <b-nav-item href="#">Dispense medication</b-nav-item>
            <b-nav-item href="#">Request days off</b-nav-item>
        </b-navbar-nav>
        
        <!-- Right aligned nav items -->
        <b-navbar-nav class="ml-auto">
            <Search :path="'/PharmacistPage'"/>

            <b-nav-item href="#" style="margin-left: 10px;">
                <b-icon-calendar-2-week></b-icon-calendar-2-week>
            </b-nav-item>
            <b-nav-item-dropdown right>
                <template #button-content>
                    <em class="pl-2">User</em>
                </template>
                <b-dropdown-item href="#">Profile</b-dropdown-item>
                <b-dropdown-item href="#">Sign Out</b-dropdown-item>
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
