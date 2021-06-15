<template>
  
  <div>
    <h3> Order list preview</h3> <hr>
    <div class="row row-cols-2 row-cols-md-3 g-2">
    <div
      class="col-lg-4 col-md-6 col-sm-10 offset-md-0 offset-sm-1"
      v-for="o in this.orders"
      :key="o.id">
      <div>
        <SupplierOrder :order="o"> </SupplierOrder>
      </div>
    </div>
    </div>
  </div>
</template>

<script>
import { defineComponent } from "@vue/composition-api";
import SupplierOrder from "../components/SupplierOrder"
export default defineComponent({
  name: "OrderList",
  components: {
    SupplierOrder
  },
  data(){
    return {
      orders: [],
      user: null,
    }
  },
  mounted(){
    this.user = JSON.parse(
        atob(localStorage.getItem("token").split(".")[1])
      ).sub;

    this.axios
      .get( process.env.VUE_APP_API_URL + `/orders/forOffer`, {
        headers: { Authorization: "Bearer " + localStorage.getItem("token") },
      })
      .then((response) => {
        this.orders = response.data;
        //console.log(this.medicaments);
      })
      .catch((error) => console.log(error.response.data));
  }
})
</script>

<style>

</style>