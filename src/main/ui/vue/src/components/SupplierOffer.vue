<template>
  <div class="card" style="width: 18rem;">
  <div class="card-header">
    <div class="row-group">
      
    <div v-if="this.offer.status === 'ACCEPTED'">
      <span class="badge badge-success">Accepted</span>
    </div>  
    <div v-else-if="this.offer.status === 'REJECTED'">
     <span class="badge badge-danger">Rejected</span>
    </div>
    <div v-else>
      <span class="badge badge-warning">Pending</span>
    </div>
    <div> Deadline: {{ this.frontEndDateFormat(this.offer.deadline) }}</div>
  </div>
  </div>
  <ul class="list-group list-group-flush">
    <li class="list-group-item"><b class="color-text">Id: </b>{{this.offer.id}}</li>
    <li class="list-group-item"><b class="color-text">Pharmacy: </b>{{this.offer.order.admin.pharmacy.name}}</li>
    <li class="list-group-item"><b class="color-text">Medicament order</b>
      <div v-for="mi in this.offer.order.medicamentItems"
          :key="mi.id">
        <p class="card-text">{{mi.medicament.name}}: {{mi.quantity}}</p>
      </div>
      <b class="color-text">Total price: {{this.offer.totalPrice}} din</b>
    </li>
    <li class="list-group-item"><button type="button" class="btn btn-secondary">Edit</button></li>
  </ul>
</div>
</template>

<script>
import { defineComponent } from "@vue/composition-api";
import moment from "moment";

export default defineComponent({
name: "SupplierOffer",
props: {
  offer: Object
},
methods: {
  frontEndDateFormat: function (date) {
      return moment(date, "YYYY-MM-DD").format("DD.MM.YYYY");
    }
}
})
</script>

<style>
.color-text{
  color: rgb(19, 94, 29);
}
</style>