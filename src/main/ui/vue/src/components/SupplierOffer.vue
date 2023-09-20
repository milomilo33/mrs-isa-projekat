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
  <b-alert v-model="showDismissibleAlert" dismissible fade variant="danger">
        Could not edit offer.
      </b-alert>
      <b-alert v-model="showSuccessAlert" dismissible fade variant="success">
        Success! You offer has been updated.
      </b-alert>
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
    <li class="list-group-item">
      <button type="button" class="btn btn-secondary" 
      v-bind:disabled="editPossible" 
      data-toggle="modal"
      :data-target="`#exampleModalCenter${offer.id}`">
        Edit
      </button>
    </li>
  </ul>
  <div
      class="modal fade"
      :id="`exampleModalCenter${offer.id}`"
      tabindex="-1"
      role="dialog"
      aria-labelledby="exampleModalCenterTitle"
      aria-hidden="true"
    >
      <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title header-color" id="exampleModalLongTitle">
              <b>Write offer</b>
            </h5>
            <button
              type="button"
              class="close"
              data-dismiss="modal"
              aria-label="Close"
            >
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <div class="form-row">
              <template>
                <div class="form-row">
                  <label>Deadline: </label>
                  <b-form-datepicker
                    v-model="deadline"
                    :min="min"
                    :max="max"
                    locale="en"
                    class="col-md-12"
                  ></b-form-datepicker>
                  <label name="totalPrice">Total Price</label>
                  <input
                    type="number"
                    class="form-control col-md-12"
                    v-model="offer.totalPrice"
                    min="1"
                    required
                  />
                </div>
                <p v-show="showDismissibleAlertModal"  class="red-color">
                  Total price must be greater than 0.
                </p>
              </template>
            </div>
          </div>
          <div class="modal-footer">
            <button
              type="button"
              class="btn btn-secondary"
              data-dismiss="modal"
            >
              Close
            </button>
            <button type="button" class="btn btn-primary" @click="editOffer">
              Send offer
            </button>
          </div>
        </div>
      </div>  
  </div>
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
data() {
   const now = new Date();
    const today = new Date(now.getFullYear(), now.getMonth(), now.getDate());
    // 15th two months prior
    const minDate = new Date(today);
    // 15th in two months
    const maxDate = new Date(today);
    maxDate.setMonth(maxDate.getMonth() + 2);
    maxDate.setDate(15);
  return {
    min: minDate,
    max: maxDate,
    showDismissibleAlertModal: false,
    showDismissibleAlert: false,
    showSuccessAlert: false,
    editPossible: true,
    supplier: null,
    deadline: "",
  }
},
mounted(){

  if(moment().format('DD. MMM YYYY.') < this.convertDate(this.offer.order.deadline)){
    this.editPossible = false;
  }
  else{
    this.editPossible = true;
  }
  this.supplier = JSON.parse(
      atob(localStorage.getItem("token").split(".")[1])
    ).sub;
},
methods: {
  convertDate : function(date){
            return moment(date).format('DD. MMM YYYY.');
        },
  frontEndDateFormat: function (date) {
      return moment(date, "YYYY-MM-DD").format("DD.MM.YYYY");
    },
  backEndDateFormat: function(date){
    return moment(date, "YYYY-MM-DD").format("YYYY-MM-DD");
  },
    editOffer: function(){
     
      var dd = this.deadline+ "T00:00:00";
      console.log(this.deadline);
      if(this.deadline==""){
        dd = this.backEndDateFormat(this.offer.deadline)+"T00:00:00";
      }
      if(this.totalPrice < 1){
         this.showDismissibleAlertModal = true;
      }
      else{
        console.log(dd);
          this.axios
        .post(process.env.VUE_APP_API_URL+
          `/offers/update`,
          {
            id: this.offer.id,
            deadline: dd,
            status: this.offer.status,
            supplier: this.supplier,
            totalPrice: this.offer.totalPrice,
            order: this.offer.order,
          },
          {
            headers: {
              Authorization: "Bearer " + localStorage.getItem("token"),
            },
          }
        )
        .then((response) => {
          if (response.data != null) {
            this.showSuccessAlert = true;
            this.showDismissibleAlertModal = false;
            this.showDismissibleAlert = false;
            this.offer.deadline = response.data.deadline;
            this.deadline="";
          }
        })
        .catch((error) => {
          console.log(error);
          this.showDismissibleAlert = true;
        });
      }
    }
}
})
</script>

<style>
.color-text{
  color: var(--green);
}
</style>