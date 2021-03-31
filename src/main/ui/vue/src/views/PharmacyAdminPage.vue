<template>
  <div>
    <b-navbar fixed="top" toggleable="lg" type="dark" variant="dark">
    <b-navbar-brand href="/PharmacyAdminPage">Home Page</b-navbar-brand>
    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
    <b-collapse id="nav-collapse" is-nav>
      <b-navbar-nav>
        <b-nav-item-dropdown right>
           <template #button-content>
            Pharmacy
          </template>
          <b-dropdown-item href="#">Appointments</b-dropdown-item>
          <b-dropdown-item href="#">Medicaments</b-dropdown-item>
          <b-dropdown-item href="#">Pricelist</b-dropdown-item>
          <b-dropdown-item href="#">Orders</b-dropdown-item>
          <b-dropdown-item href="#">Offers</b-dropdown-item>
          <b-dropdown-item href="#">Promotions</b-dropdown-item>
        </b-nav-item-dropdown>
        <b-nav-item-dropdown right>
           <template #button-content>
            Employees
          </template>
          <b-dropdown-item href="#">Dermatologists</b-dropdown-item>
          <b-dropdown-item href="/PharmacyAdminPage/EmployeeTable">Pharmacists</b-dropdown-item>
        </b-nav-item-dropdown>
        <b-nav-item href="#">Reports</b-nav-item>
      </b-navbar-nav>
      
      <!-- Right aligned nav items -->
      <b-navbar-nav class="ml-auto">
        <b-nav-form>
          <b-form-input size="sm" class="mr-sm-2" placeholder="Search"></b-form-input>
          <b-button size="sm" class="my-2 my-sm-0" type="submit">Search</b-button>
        </b-nav-form>

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