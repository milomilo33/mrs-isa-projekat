<template>
  <div>
    <b-navbar fixed="top" toggleable="lg" type="light" variant="light">
    <b-navbar-brand href="/PharmacyAdminPage">Home Page</b-navbar-brand>
    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
    <b-collapse id="nav-collapse" is-nav>
      <b-navbar-nav>
        <b-nav-item href="/PharmacyAdminPage/MedicamentList">Medicaments</b-nav-item>
        <b-nav-item href="/PharmacyAdminPage/PharmacyList"> Pharmacies</b-nav-item>
        <b-nav-item-dropdown right>
           <template #button-content>
            Pharmacy
          </template>
          <b-dropdown-item href="#">Profile</b-dropdown-item>
          <b-dropdown-item href="#">Appointments</b-dropdown-item>
          <b-dropdown-item href="/PharmacyAdminPage/MedicamentTable">Medicaments</b-dropdown-item>
          <b-dropdown-item href="/PharmacyAdminPage/PricelistTable">Pricelist</b-dropdown-item>
          <b-dropdown-item href="#">Orders</b-dropdown-item>
          <b-dropdown-item href="#">Offers</b-dropdown-item>
          <b-dropdown-item href="#">Promotions</b-dropdown-item>
        </b-nav-item-dropdown>
        <b-nav-item-dropdown right>
           <template #button-content>
            Employees
          </template>
          <b-dropdown-item href="/PharmacyAdminPage/PharmacistRegistration">Registration Pharmacists</b-dropdown-item>
          <b-dropdown-item href="/PharmacyAdminPage/DermatologistTable">Dermatologists</b-dropdown-item>
          <b-dropdown-item href="/PharmacyAdminPage/PharmacistTable">Pharmacists</b-dropdown-item>
          <b-dropdown-item href="#">Requests</b-dropdown-item>
          <b-dropdown-item href="#">Vacations</b-dropdown-item>
        </b-nav-item-dropdown>
        <b-nav-item href="#">Reports</b-nav-item>
      </b-navbar-nav>
      
      <!-- Right aligned nav items -->
      <b-navbar-nav class="ml-auto">
        <Search :path="'/PharmacyAdminPage'" />

        <b-nav-item-dropdown right>
          <!-- Using 'button-content' slot -->
          <template #button-content>
            <em class="pl-2">User</em>
          </template>
          <b-dropdown-item href="#">Profile</b-dropdown-item>
          <b-dropdown-item href="/Logout">Sign Out</b-dropdown-item>
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
