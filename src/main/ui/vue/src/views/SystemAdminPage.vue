<template>
  <div>
    <b-navbar fixed="top" toggleable="lg" type="dark" variant="dark">
    <b-navbar-brand href="/SystemAdminPage">Home Page</b-navbar-brand>
    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
    <b-collapse id="nav-collapse" is-nav>
      <b-navbar-nav>
        <b-nav-item-dropdown right>
           <template #button-content>
            User Registration
          </template>
          <b-dropdown-item href="#">Pharmacy Admin</b-dropdown-item>
          <b-dropdown-item href="#">System Admin</b-dropdown-item>
          <b-dropdown-item href="#">Pharmacist</b-dropdown-item>
          <b-dropdown-item href="#">Supplier</b-dropdown-item>
        </b-nav-item-dropdown>
        <b-nav-item href="/SystemAdminPage/pharmacyRegistration">Pharmacy Registration</b-nav-item>
        <b-nav-item href="#">Medicament Registration</b-nav-item>
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