<template>
  <div>
    <h3> Offer List preview</h3> <hr>
    <div class="row row-cols-2 row-cols-md-3 g-2">
    <div
      class="col-lg-4 col-md-6 col-sm-10 offset-md-0 offset-sm-1"
      v-for="o in this.offers"
      :key="o.id">
      <div>
       <SupplierOffer :offer="o"> </SupplierOffer>
      </div>
    </div>
    </div>
  </div>
</template>

<script>
import { defineComponent } from "@vue/composition-api";

import SupplierOffer from '../components/SupplierOffer'
export default defineComponent({
   components: {
    SupplierOffer
  },
  data(){
    return {
      offers: null,
      user: "",
    }
  }, 
  mounted(){
     this.user = JSON.parse(
        atob(localStorage.getItem("token").split(".")[1])
      ).sub;

    this.axios
      .get(`/api/offers/`+this.user, {
        headers: { Authorization: "Bearer " + localStorage.getItem("token") },
      })
      .then((response) => {
        this.offers = response.data;
        //console.log(this.medicaments);
      })
      .catch((error) => console.log(error.response.data));
  }
});
</script>
<style scoped>

</style>