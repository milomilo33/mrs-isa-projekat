<template>
    <div id="PharmacyListPreview">
        <!-- POSLE CE DA IDE POSEBNA KOMPONENTA -->
        <b-navbar fixed="top" toggleable="lg" type="dark" variant="dark">
    <b-navbar-brand href="#">Home Page</b-navbar-brand>
    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
    <b-collapse id="nav-collapse" is-nav>
      <b-navbar-nav>
        <b-nav-item href="#">All Products</b-nav-item>
        <b-nav-item href="#">Contact</b-nav-item>
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

    <PharmacyCard />
    

    </div>    
</template>

<script>
import { defineComponent } from '@vue/composition-api'
import PharmacyCard from '../components/PharmacyCard'
import Search from '../components/Search'


export default defineComponent({
    name: "PharmacyListPreview",
    components: {
        PharmacyCard,
        Search
    },
    
    data() {
      return {
        pharmacies: ''
      }
    },
    
})

</script>


