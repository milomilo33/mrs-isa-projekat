<template>
  <div>
    <b-navbar fixed="top" toggleable="lg" type="light" variant="light">
    <b-navbar-brand href="/SupplierPage/PhharmacyList">Home Page</b-navbar-brand>
    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
    <b-collapse id="nav-collapse" is-nav>
      <b-navbar-nav>
        <b-nav-item href="/SupplierPage/MedicamentList">Medicaments</b-nav-item>
        <b-nav-item href="/SupplierPage/PharmacyList"> Pharmacies</b-nav-item>
        <b-nav-item href="/SupplierPage/OrderList">Order List</b-nav-item>
        <b-nav-item href="/SupplierPage/OfferList">Offer List</b-nav-item>
      </b-navbar-nav>
      
      <!-- Right aligned nav items -->
      <b-navbar-nav class="ml-auto">
        <Search :path="'/SupplierPage'" />

        <b-nav-item-dropdown right>
          <!-- Using 'button-content' slot -->
          
          <template #button-content>
            <em class="pl-2">User</em>
          </template>
          <b-dropdown-item href="#">Profile</b-dropdown-item>
           <b-dropdown-item href="/SupplierPage/ChangePassword">Change password</b-dropdown-item>
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
