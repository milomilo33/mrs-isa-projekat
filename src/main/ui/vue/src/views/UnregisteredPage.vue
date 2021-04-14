<template>
  <div>
    <b-navbar fixed="top" toggleable="lg" type="light" variant="light">
    <b-navbar-brand href="/UnregisteredPage">Home Page</b-navbar-brand>
    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
    <b-collapse id="nav-collapse" is-nav>
      <b-navbar-nav>
        <b-nav-item href="/UnregisteredPage/MedicamentList">Medicaments</b-nav-item>
        <b-nav-item href="/UnregisteredPage/PharmacyList"> Pharmacies</b-nav-item>
      </b-navbar-nav>
      
      <!-- Right aligned nav items -->
      <b-navbar-nav class="ml-auto">
        <Search :path="'/UnregisteredPage'" />

        <b-nav-item-dropdown right>
          <!-- Using 'button-content' slot -->
          
          <template #button-content>
            <em class="pl-2">User</em>
          </template>
          <b-dropdown-item v-show="false" href="#">Profile</b-dropdown-item>
          <b-dropdown-item href="/UnregisteredPage/PatientRegistration">Sign In</b-dropdown-item>
        </b-nav-item-dropdown>
      </b-navbar-nav>
    </b-collapse>
    </b-navbar>
    <router-view>

    </router-view>
  </div>
</template>

<script>
import Search from '../components/Search'


export default {
  components: {
    Search
  }
}
</script>
