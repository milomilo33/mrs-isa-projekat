<template>
  <div>
    <b-navbar fixed="top" toggleable="lg" type="light" variant="light">
    <b-navbar-brand href="/SystemAdminPage">Home Page</b-navbar-brand>
    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
    <b-collapse id="nav-collapse" is-nav>
      <b-navbar-nav>
        <b-nav-item href="/SystemAdminPage/MedicamentList">Medicaments</b-nav-item>
        <b-nav-item href="/SystemAdminPage/PharmacyList"> Pharmacies</b-nav-item>
        <b-nav-item-dropdown right>
           <template #button-content>
            User Registration
          </template>
          <b-dropdown-item href="/SystemAdminPage/userRegister/PharmacyAdmin">Pharmacy Admin</b-dropdown-item>
          <b-dropdown-item href="/SystemAdminPage/userRegister/SystemAdmin">System Admin</b-dropdown-item>
          <b-dropdown-item href="/SystemAdminPage/userRegister/Dermatologist">Dermatologist</b-dropdown-item>
          <b-dropdown-item href="/SystemAdminPage/userRegister/Supplier">Supplier</b-dropdown-item>
        </b-nav-item-dropdown>
        <b-nav-item href="/SystemAdminPage/pharmacyRegistration">Pharmacy Registration</b-nav-item>
        <b-nav-item href="/SystemAdminPage/MedicamentRegistration">Medicament Registration</b-nav-item>
      </b-navbar-nav>
      
      <!-- Right aligned nav items -->
      <b-navbar-nav class="ml-auto">
        <Search :path="'/SystemAdminPage'" />

        <b-nav-item-dropdown right>
          <!-- Using 'button-content' slot -->
          
          <template #button-content>
            <em class="pl-2">User</em>
          </template>
          <b-dropdown-item href="#">Profile</b-dropdown-item>
          <b-dropdown-item href="#">Sign Out</b-dropdown-item>
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
