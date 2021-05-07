<template>
  <div class="card">
  <div class="card-body">
    <h5 class="card-title">Medicaments required: </h5>
    <div v-for="mi in this.order.medicamentItems"
          :key="mi.id">
    <p class="card-text">{{mi.medicament.name}}: {{mi.quantity}}</p>
    </div>
    <a href="#" class="btn btn-secondary">Write offer</a>
  </div>
</div>
</template>

<script>
import { defineComponent } from "@vue/composition-api";

export default defineComponent({
name: "SupplierOrder",
props: {
  order: Object
}
})
</script>

<style>

</style>